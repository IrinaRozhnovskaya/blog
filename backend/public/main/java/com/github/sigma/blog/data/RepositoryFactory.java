package com.github.sigma.blog.data;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class RepositoryFactory {

    @Produces
    @Dependent
    @PersistenceUnit
    EntityManagerFactory emf;

    @Produces
    @RequestScoped
    public EntityManager em() {
        final EntityManager em = emf.createEntityManager();
        return em;
    }

    public void close(@Disposes EntityManager em) {
        em.close();
    }
}
