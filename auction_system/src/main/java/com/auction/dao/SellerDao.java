package com.auction.dao;

import javax.persistence.EntityManager;

import com.auction.entities.Seller;

public interface SellerDao extends ASBaseDao<String, Seller>
{
	 public void setEntityManager(EntityManager entityManager);
	 
	// public List<Item> getItemsOfferedBySeller(Seller seller);
}
