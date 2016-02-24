package com.auction.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.auction.dao.ASBaseDao;
import com.auction.dao.ItemDao;
import com.auction.entities.Item;
import com.auction.entities.User;

@SuppressWarnings("restriction")
public class ItemServiceImpl extends ASBaseServiceImpl<Long, Item> implements ItemService
{
	@Autowired
	private ItemDao itemDao;
	
	@SuppressWarnings("restriction")
	@PostConstruct
	public void init() throws Exception 
	{
		super.setDAO((ASBaseDao) itemDao);
	}
	
	public void setEntityManagerOnDao(EntityManager entityManager)
    {
		itemDao.setEntityManager(entityManager);
    }
    
	public void setItemDao(ItemDao itemDao) 
	{
		this.itemDao = itemDao;
	}

	public List<Item> getItemsOfferedBySeller(User user)
	{
		return itemDao.getItemsOfferedBySeller(user);
	}
	
	public Item findByName(String name, String description)
	{
		return itemDao.findByName(name, description);
	}
}
