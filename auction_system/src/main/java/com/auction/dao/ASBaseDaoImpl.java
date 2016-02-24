package com.auction.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ASBaseDaoImpl<E, K> implements ASBaseDao<E, K>
{
	EntityManagerFactory entityManagerFactory;

    EntityManager entityManager;
    
    protected Class<E> entityClass;


    @SuppressWarnings("unchecked")
    public ASBaseDaoImpl()
    {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
    }
    
    public EntityManager getEntityManager()
    {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public EntityManagerFactory getEntityManagerFactory()
    {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory)
    {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void persist(E entity)
    {
        entityManager.persist(entity);
        entityManager.flush();
    }

    public void remove(E entity)
    {
    	entityManager.remove(entity);
    	entityManager.flush();
    }

    public E merge(E entity)
    {
        E e = entityManager.merge(entity);
        return e;
    }

    public E findById(K id)
    {
        return entityManager.find(entityClass, id);
    }
    
    public List<E> findAll()
    {
        return entityManager.createQuery("SELECT h FROM " + entityClass.getName() + " h ORDER BY h.id ASC").getResultList();
    }

    public E flush(E entity)
    {
    	entityManager.flush();
        return entity;
    }

}
