package com.xinmy.entity;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserTest {
    @Autowired
    SessionFactory sessionFactory;
    Session session;

    @Test
    public void save() {
        long begin = new Date().getTime();
        for (int i = 0; i < 100; i++) {
            session.beginTransaction();
            User user = new User("transaction" + i, "123456");
            session.save(user);
            session.getTransaction().commit();
        }
        //总耗时：14167
        log.info("transaction总耗时{}", new Date().getTime() - begin);
    }

    @Test
    public void save2() {
        long begin = new Date().getTime();
        for (int i = 0; i < 100; i++) {
            User user = new User("session" + i, "123456");
            session.save(user);
            session.flush();
        }
        //总耗时：10985
        log.info("session总耗时{}", new Date().getTime() - begin);
    }


    @Test
    public void query() {
        log.info("程序开始！！");
        long begin = new Date().getTime();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", "lijianxin"));
        criteria.setLockMode(LockMode.UPGRADE);
        criteria.list().forEach(u -> {
            User user = (User) u;
            String oldName = user.getUsername();
            user.setUsername(oldName + "lock");
            session.save(user);
            log.info("用户名由{}->{}", oldName, user.getUsername());
        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Session session2 = sessionFactory.openSession();
                log.info("线程2 || session == session2 {}", session == session2);
                long begin2 = new Date().getTime();
                session2.beginTransaction();
                Criteria criteria = session2.createCriteria(User.class);
                criteria.add(Restrictions.eq("username", "lijianxin"));
                criteria.list().forEach(u -> {
                    User user = (User) u;
                    String oldName = user.getUsername();
                    user.setUsername(oldName + "lock2");
                    session2.save(user);
                    log.info("线程2用户名由{}->{}", oldName, user.getUsername());
                });
                session2.getTransaction().commit();
                log.info("线程2时{}", new Date().getTime() - begin2);
            }
        };
        Thread thread = new Thread(runnable, "线程2");
        thread.start();
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        session.getTransaction().commit();
        //总耗时：
        log.info("session总耗时{}", new Date().getTime() - begin);
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        session = sessionFactory.openSession();
    }

    @After
    public void tearDown() throws Exception {
        if (null != session) {
            session = sessionFactory.openSession();
        }
    }
}