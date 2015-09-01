package com.andaily.infrastructure.hibernate.queryhelper.impl;

import com.andaily.domain.application.ApplicationInstance;
import com.andaily.infrastructure.hibernate.queryhelper.AbstractQueryHelper;
import com.andaily.infrastructure.hibernate.queryhelper.ParameterFilter;
import com.andaily.infrastructure.hibernate.queryhelper.SortCriterionFilter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Map;

/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceListQueryHelper extends AbstractQueryHelper<ApplicationInstance> {

    private Map<String, Object> map;

    public ApplicationInstanceListQueryHelper(Session session, Map<String, Object> map) {
        super(session);
        this.map = map;

        addInstanceNameFilter();
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

    private void addInstanceNameFilter() {
        final String instanceName = (String) map.get("instanceName");
        if (StringUtils.isNotEmpty(instanceName)) {
            addFilter(new ParameterFilter() {
                @Override
                public void setParameter(Query query) {
                    query.setParameter("instanceName", "%" + instanceName + "%");
                }

                @Override
                public String getSubHql() {
                    return " and ai.instanceName like :instanceName ";
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
        return " select count(ai.id) from ApplicationInstance ai where ai.archived = false ";
    }

    @Override
    public String getResultHql() {
        return " from ApplicationInstance ai where ai.archived = false ";
    }
}
