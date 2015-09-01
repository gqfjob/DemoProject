package com.andaily.infrastructure.hibernate.queryhelper;

import org.hibernate.Query;

/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */
public abstract class ParameterFilter implements Filter {
    public abstract void setParameter(Query query);
}
