package com.auction.service;

import java.util.List;

import javax.persistence.EntityManager;

public interface ASBaseService<K,E>
{
	public void setEntityManagerOnDao(EntityManager entityManager) throws Exception;
	
	public E find(K id) throws Exception;
	
	public List<E> findAll() throws Exception;;
	
	public E saveOrUpdate(E entity) throws Exception;
    
    public void deleteIfExisting(K id) throws Exception;

}
