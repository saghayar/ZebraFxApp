/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.com.cybernet.zebra.db;

/**
 *
 * @author SAMIR-PC
 */

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;



public class DbImpl {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public DbImpl() {

    }

    public DbImpl(String moduleName) {
        entityManager = getEmf(moduleName).createEntityManager();
    }

    public static EntityManagerFactory getEmf(String persistenceUnitName) {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        }
        return entityManagerFactory;
    }

    public EntityManager getEm() {
        return entityManager;
    }

    public EntityTransaction getTransaction() {
        return getEm().getTransaction();
    }

    public void beginTransaction() {
        getTransaction().begin();
    }

    public void commitTransaction() {
        getEm().getTransaction().commit();
        // getEm().flush();

    }

    public void rollbackTransaction() {
        getEm().getTransaction().rollback();
    }

    public void closeEm() {

        if (getEm() != null || getEm().isOpen()) {
            // getEm().flush();
            // getEm().clear();
            getEm().close();
        }

    }

}
