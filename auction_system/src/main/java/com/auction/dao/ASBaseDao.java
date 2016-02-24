package com.auction.dao;

import java.util.List;

import javax.persistence.EntityManager;

public interface ASBaseDao<E, K>
{
	public void setEntityManager(EntityManager entityManager);

	public void persist(E entity);

	public void remove(E entity);

	public E merge(E entity);

	public E findById(K id);
	
	public List<E> findAll();

	public E flush(E entity);
}
