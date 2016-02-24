package com.auction.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.auction.entities.Seller;

public class SellerDaoImpl extends ASBaseDaoImpl<String, Seller> implements SellerDao
{
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@PersistenceContext(unitName = "ASPersistenceUnit")
	private EntityManager entityManager;

	@PostConstruct
	public void init()
	{
		super.setEntityManagerFactory(entityManagerFactory);
		super.setEntityManager(entityManager);
	}

//	public List<Item> getItemsOfferedBySeller(Seller seller)
//	{
//		return null;
//	}
}
