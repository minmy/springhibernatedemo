package com.xinmy.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserTest {
    @Autowired
    SessionFactory sessionFactory;
    Session session;

    @Test
    public void save() {
        session.beginTransaction();
        User user = new User("lijianxin", "123456");
        session.save(user);
        session.getTransaction().commit();
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
