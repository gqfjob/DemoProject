package com.andaily.infrastructure;

import com.andaily.ContextTest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractRepositoryTest extends ContextTest {

    @Autowired
    private SessionFactory sessionFactory;


    protected Session session() {
        return sessionFactory.getCurrentSession();
    }

    protected void fullClean() {
        session().flush();
        session().clear();
    }
}