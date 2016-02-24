package com.auction.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.auction.dao.ASBaseDao;

public class ASBaseServiceImpl<K,E> implements ASBaseService<K,E>
{
	private ASBaseDao<E, K> dao;

	protected Class<E> entityClass;


	@SuppressWarnings("unchecked")
	public ASBaseServiceImpl() 
	{
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	public void setEntityManagerOnDao(EntityManager entityManager) 
	{
		dao.setEntityManager(entityManager);
	}

	protected void setDAO(ASBaseDao<E, K> dao) 
	{
		this.dao = dao;
	}

	public E find(K id) throws Exception 
	{
		try 
		{
			return (E) dao.findById(id);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

			throw new Exception();
		}
	}

	public List<E> findAll() throws Exception 
	{
		try 
		{
			return dao.findAll();
		} 
		catch (Exception e)
		{
			e.printStackTrace();

			throw new Exception();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public E saveOrUpdate(E entity) throws Exception 
	{
		try 
		{
			entity = dao.merge(entity);
			return dao.flush(entity);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

			throw new Exception();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteIfExisting(K id) throws Exception 
	{
		try 
		{
			E e = find(id);
			if (e != null) 
			{
				dao.remove(e);
				dao.flush(e);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

			throw new Exception();
		}
	}
}
