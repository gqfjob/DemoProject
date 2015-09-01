package com.andaily.infrastructure.hibernate.queryhelper;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */
public abstract class AbstractQueryHelper<T> implements QueryHelper<T> {

    protected final static Logger LOG = LoggerFactory.getLogger(AbstractQueryHelper.class);

    private List<Filter> filters = new ArrayList<>();
    private List<SortCriterionFilter> sortCriterionFilters = new ArrayList<>();
    protected Session session;

    protected AbstractQueryHelper() {
    }

    protected AbstractQueryHelper(Session session) {
        this.session = session;
    }

    public void addFilter(Filter filter) {
        if (filter != null) {
            filters.add(filter);
        }
    }

    /**
     * Do not use add any <code>order by </code> key words in the <code>getSubHql</code> method,
     * just add the sort filed and sort direction.
     * <p/>
     * <code>
     * addSortCriterionFilter(new SortCriterionFilter() {
     * public String getSubHql() {
     * return " p.createdDateTime desc ";
     * }
     * });
     * </code>
     * <p/>
     * SortCriterionFilter only use when call {@link #getResults()} .
     *
     * @param sortCriterionFilter SortCriterionFilter instance
     */
    public void addSortCriterionFilter(SortCriterionFilter sortCriterionFilter) {
        if (sortCriterionFilter != null) {
            this.sortCriterionFilters.add(sortCriterionFilter);
        }
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public String getSubHql() {
        StringBuilder subHql = new StringBuilder();
        List<Filter> filters = getFilters();
        for (Filter filter : filters) {
            subHql.append(filter.getSubHql());
        }
        return subHql.toString();
    }

    public String getSortHql() {
        StringBuilder sortHql = new StringBuilder();
        if (!this.sortCriterionFilters.isEmpty()) {
            sortHql.append(" order by ");
            int lastFilterIndex = sortCriterionFilters.size() - 1;
            for (SortCriterionFilter sortCriterionFilter : sortCriterionFilters) {
                sortHql.append(sortCriterionFilter.getSubHql());
                int currentIndex = sortCriterionFilters.indexOf(sortCriterionFilter);
                if (currentIndex != lastFilterIndex) {
                    sortHql.append(",");
                }
            }
        }
        return sortHql.toString();
    }

    public abstract String getResultHql();

    public int getStartPosition() {
        return 0;
    }

    public int getItemsAmountPerPage() {
        return 0;
    }

    public String getAmountHql() {
        throw new UnsupportedOperationException("Not yet implemented,please override it");
    }

    public Integer getAmount() {
        final String amountHql = getAmountHql() + getSubHql();
        LOG.debug("Amount hql:" + amountHql);
        Query query = createQuery(amountHql);
        return ((Long) query.uniqueResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    public List<T> getResults() {
        final String resultHql = getResultHql() + getSubHql() + getSortHql();
        LOG.debug("Result sql: " + resultHql);
        Query query = createQuery(resultHql);
        int amountPerPage = getItemsAmountPerPage();
        if (amountPerPage == 0) {
            return query.list();
        }
        int startPosition = getStartPosition();
        return query.setMaxResults(amountPerPage)
                .setFirstResult(startPosition)
                .list();
    }

    //返回唯一一个结果
    @Override
    @SuppressWarnings("unchecked")
    public T uniqueResult() {
        final String resultHql = getResultHql() + getSubHql();
        LOG.debug("Result sql: " + resultHql);
        Query query = createQuery(resultHql);
        return (T) query.uniqueResult();
    }

    protected Query createQuery(String resultHql) {
        Query query = session.createQuery(resultHql);
        List<Filter> filters = getFilters();
        for (Filter filter : filters) {
            if (filter instanceof ParameterFilter) {
                ParameterFilter parameterFilter = (ParameterFilter) filter;
                parameterFilter.setParameter(query);
            }
        }
        return query;
    }
}

