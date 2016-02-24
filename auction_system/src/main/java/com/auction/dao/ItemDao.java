package com.auction.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.auction.entities.Item;
import com.auction.entities.User;

public interface ItemDao extends ASBaseDao<Item, Long> 
{
	public void setEntityManager(EntityManager entityManager);
	
	public List<Item> getItemsOfferedBySeller(User user);
	
	public Item findByName(String name, String description);
}
