package com.andaily.infrastructure.hibernate.queryhelper;

import java.util.List;

/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */
public interface QueryHelper<T> {
    String getResultHql();

    List<T> getResults();

    T uniqueResult();
}
