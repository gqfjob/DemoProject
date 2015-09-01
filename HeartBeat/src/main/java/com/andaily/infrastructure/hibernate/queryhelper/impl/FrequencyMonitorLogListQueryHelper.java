package com.andaily.infrastructure.hibernate.queryhelper.impl;

import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.infrastructure.hibernate.queryhelper.AbstractQueryHelper;
import com.andaily.infrastructure.hibernate.queryhelper.ParameterFilter;
import com.andaily.infrastructure.hibernate.queryhelper.SortCriterionFilter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Map;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */
public class FrequencyMonitorLogListQueryHelper extends AbstractQueryHelper<FrequencyMonitorLog> {

    private Map<String, Object> map;

    public FrequencyMonitorLogListQueryHelper(Session session, Map<String, Object> map) {
        super(session);
        this.map = map;

        addInstanceGuidFilter();
        addNormalFilter();
        addSortFilter();
    }

    private void addNormalFilter() {
        final Integer normal = (Integer) map.get("normal");
        if (normal == 1 || normal == 2) {
            addFilter(new ParameterFilter() {
                @Override
                public void setParameter(Query query) {
                    query.setParameter("normal", normal == 1);
                }

                @Override
                public String getSubHql() {
                    return " and ai.normal = :normal";
                }
            });
        }
    }

    private void addSortFilter() {
        final String orderItem = (String) map.get("orderItem");
        addSortCriterionFilter(new SortCriterionFilter() {
            @Override
            public String getSubHql() {
                return " ai." + orderItem + " desc ";
            }
        });
    }

    private void addInstanceGuidFilter() {
        final String instanceGuid = (String) map.get("instanceGuid");
        if (StringUtils.isNotEmpty(instanceGuid)) {
            addFilter(new ParameterFilter() {
                @Override
                public void setParameter(Query query) {
                    query.setParameter("instanceGuid", instanceGuid);
                }

                @Override
                public String getSubHql() {
                    return " and ai.instance.guid = :instanceGuid ";
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
        return " select count(ai.id) from FrequencyMonitorLog ai where ai.archived = false ";
    }

    @Override
    public String getResultHql() {
        return " from FrequencyMonitorLog ai where ai.archived = false ";
    }
}
