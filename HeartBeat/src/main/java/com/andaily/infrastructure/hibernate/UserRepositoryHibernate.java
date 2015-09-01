package com.andaily.infrastructure.hibernate;

import com.andaily.domain.AbstractDomain;
import com.andaily.domain.user.*;
import com.andaily.infrastructure.hibernate.queryhelper.impl.ListUsersQueryHelper;
import com.google.common.collect.ImmutableMap;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Shengzhao Li
 */
@Repository("userRepository")
public class UserRepositoryHibernate extends AbstractRepositoryHibernate<User> implements UserRepository {

    @Override
    public User findByUsername(String username) {
        final List<User> list = find("from User u where u.username = :username and u.archived = false", ImmutableMap.of("username", username));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Privilege> findUserPrivileges(User user) {
        final String hql = " select up.privilege from UserPrivilege up where up.archived = false and up.user = :user";
        final Query query = session().createQuery(hql)
                .setParameter("user", user);
        return query.list();
    }

    @Override
    public List<User> findListUsers(Map<String, Object> map) {
        ListUsersQueryHelper queryHelper = new ListUsersQueryHelper(session(), map);
        return queryHelper.getResults();
    }

    @Override
    public int totalListUsers(Map<String, Object> map) {
        ListUsersQueryHelper queryHelper = new ListUsersQueryHelper(session(), map);
        return queryHelper.getAmount();
    }

    @Override
    public User findByUsernameIgnoreArchived(String username) {
        final List<User> list = find("from User u where u.username = :username ", ImmutableMap.of("username", username));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int deleteUserPrivileges(User user) {
        String hql = " from UserPrivilege up where up.user = :user ";
        final List<UserPrivilege> privileges = find(hql, ImmutableMap.of("user", user));
        deleteAll(privileges);
        return privileges.size();
    }

    @Override
    public SystemSetting findSystemSetting() {
        String hql = " from SystemSetting ss where ss.archived = false ";
        SystemSetting systemSetting = (SystemSetting) session().createQuery(hql).uniqueResult();
        if (systemSetting == null) {
            systemSetting = new SystemSetting();
        }
        return systemSetting;
    }
}