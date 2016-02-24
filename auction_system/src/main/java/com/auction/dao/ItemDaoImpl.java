package com.auction.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.auction.entities.Item;
import com.auction.entities.User;

public class ItemDaoImpl extends ASBaseDaoImpl<Item, Long> implements ItemDao
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
	
	public List<Item> getItemsOfferedBySeller(User user)
	{
		List<Item> allItems = findAll();
		
		List<Item> itemsOfferedByUser = new ArrayList<>();
		
		for (Item item : allItems) 
		{
			if(item.getOwner().getUsername().equalsIgnoreCase(user.getUsername()))
			{
				itemsOfferedByUser.add(item);
			}
		}
		
		return itemsOfferedByUser;
	}
	
	public Item findByName(String name, String description)
	{
		Item item = null;

		TypedQuery<Item> query = entityManager.createNamedQuery(Item.FIND_BY_NAME, Item.class);

		query.setParameter("name", name);
		query.setParameter("description", description);

		try
		{
			item  = query.getSingleResult();
		}
		catch(NoResultException nre)
		{
			//no result Found in database.
		}

		return item;
	}
}
