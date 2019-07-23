package com.xinmy.query;

import com.xinmy.core.entity.AbstractEntity;
import com.xinmy.core.query.AbstractQuery;
import com.xinmy.entity.User;
import com.xinmy.entity.User.UserType;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * 用户查询类
 *
 * @author 杨海彬
 */
public class UserQuery extends AbstractQuery {

    @Override
    protected Class<? extends AbstractEntity> queryEntity() {
        return User.class;
    }

    /**
     * 添加业务代码查询条件
     */
    public UserQuery serviceCode(String serviceCode) {
        if (StringUtils.isNotEmpty(serviceCode)) {
            addCriterion(Restrictions.eq("serviceCode", serviceCode));
        }
        return this;
    }

    /**
     * 添加用户类型查询条件
     */
    public UserQuery userType(UserType userType) {
        if (userType != null) {
            addCriterion(Restrictions.eq("userType", userType));
        }
        return this;
    }

    /**
     * 添加帐号查询条件
     */
    public UserQuery username(String username) {
        if (StringUtils.isNotEmpty(username)) {
            addCriterion(Restrictions.eq("username", username));
        }
        return this;
    }

    /**
     * 添加帐号模糊查询条件
     */
    public UserQuery usernameLike(String username) {
        if (StringUtils.isNotEmpty(username)) {
            addCriterion(Restrictions.ilike("username", username, MatchMode.ANYWHERE));
        }
        return this;
    }

    /**
     * 添加用户名称模糊条件
     */
    public UserQuery fullnameLike(String fullname) {
        if (StringUtils.isNotEmpty(fullname)) {
            addCriterion(Restrictions.ilike("fullname", fullname, MatchMode.ANYWHERE));
        }
        return this;
    }

    /**
     * 添加角色多表查询条件
     */
    public UserQuery roles(RoleQuery roleQuery) {
        if (null != roleQuery) {
            addCriteria("roles", roleQuery);
        }
        return this;
    }
}