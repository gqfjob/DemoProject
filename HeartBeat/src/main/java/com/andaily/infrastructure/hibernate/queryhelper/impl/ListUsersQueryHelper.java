package com.andaily.infrastructure.hibernate.queryhelper.impl;

import com.andaily.domain.user.User;
import com.andaily.infrastructure.hibernate.queryhelper.AbstractQueryHelper;
import com.andaily.infrastructure.hibernate.queryhelper.ParameterFilter;
import com.andaily.infrastructure.hibernate.queryhelper.SortCriterionFilter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Map;

/**
 *
 * @author Shengzhao Li
 */
public class ListUsersQueryHelper extends AbstractQueryHelper<User> {

    private Map<String, Object> map;

    public ListUsersQueryHelper(Session session, Map<String, Object> map) {
        super(session);
        this.map = map;

        addUsernameFilter();
        addSortFilter();
    }

    private void addSortFilter() {
        addSortCriterionFilter(new SortCriterionFilter() {
            @Override
            public String getSubHql() {
                return " ai.createTime desc ";
            }
        });
    }

    private void addUsernameFilter() {
        final String username = (String) map.get("username");
        if (StringUtils.isNotEmpty(username)) {
            addFilter(new ParameterFilter() {
                @Override
                public void setParameter(Query query) {
                    query.setParameter("username", "%" + username + "%");
                }

                @Override
                public String getSubHql() {
                    return " and ai.username like :username ";
                }
            });
        }
    }


    @Override
    public int getStartPosition() {
        return (Integer) map.get("startIndex");
    }

    @Override
    public int getItemsAmountPerPage() {
        return (Integer) map.get("perPageSize");
    }

    @Override
    public String getAmountHql() {
        return " select count(ai.id) from User ai where ai.archived = false ";
    }

    @Override
    public String getResultHql() {
        return " from User ai where ai.archived = false ";
    }
}
