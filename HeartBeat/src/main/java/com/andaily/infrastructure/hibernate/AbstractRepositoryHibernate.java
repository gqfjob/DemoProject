package com.andaily.infrastructure.hibernate;

import com.andaily.domain.AbstractDomain;
import com.andaily.domain.shared.Repository;
import com.google.common.collect.ImmutableMap;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractRepositoryHibernate<T> implements Repository, InitializingBean {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(sessionFactory, "SessionFactory is required!");
    }

    protected Session session() {
        return sessionFactory.getCurrentSession();
    }

    /*
    * It is ext. HibernateTemplate
    */
    @SuppressWarnings("unchecked")
    protected <T extends AbstractDomain> List<T> find(final String queryString, final ImmutableMap<String, ?> paramsMap) throws DataAccessException {
        Query queryObject = session().createQuery(queryString);
        if (paramsMap != null) {
            for (String key : paramsMap.keySet()) {
                queryObject.setParameter(key, paramsMap.get(key));
            }
        }
        return queryObject.list();
    }


    protected <T extends AbstractDomain> List<T> find(final String queryString) throws DataAccessException {
        return find(queryString, null);
    }

    @Override
    public <T extends AbstractDomain> T findById(Integer id, Class<T> clazz) {
        List<T> list = find("from " + clazz.getSimpleName() + " do where do.id = :id", ImmutableMap.of("id", id));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public <T extends AbstractDomain> T findByGuid(String guid, Class<T> clazz) {
        List<T> list = find("from " + clazz.getSimpleName() + " do where do.guid = :guid", ImmutableMap.of("guid", guid));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public <T extends AbstractDomain> void saveOrUpdate(T domain) {
        session().saveOrUpdate(domain);
    }

    @Override
    public <T extends AbstractDomain> void saveOrUpdateAll(Collection<T> collection) {
        for (T t : collection) {
            saveOrUpdate(t);
        }
    }

    @Override
    public <T extends AbstractDomain> void delete(T domain) {
        session().delete(domain);
    }

    @Override
    public <T extends AbstractDomain> void deleteByGuid(Class<T> clazz, String guid) {
        final T t = findByGuid(guid, clazz);
        delete(t);
    }

    @Override
    public <T extends AbstractDomain> void deleteAll(Collection<T> elements) {
        for (T element : elements) {
            delete(element);
        }
    }

    @Override
    public <T extends AbstractDomain> List<T> findAll(Class<T> clazz, boolean active) {
        return find("from " + clazz.getName() + " c where c.archived = :archived", ImmutableMap.of("archived", !active));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractDomain> List<T> findByGuids(Class<T> clazz, List<String> guids) {
        if (guids == null || guids.isEmpty()) {
            return Collections.emptyList();
        }
        final Query query = session().createQuery("from " + clazz.getName() + " c where c.guid in (:guids)");
        query.setParameterList("guids", guids);
        return query.list();
    }
}