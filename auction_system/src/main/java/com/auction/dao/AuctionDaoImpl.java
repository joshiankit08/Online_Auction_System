package com.auction.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.auction.entities.Auction;
import com.auction.entities.AuctionStatusEnum;

public class AuctionDaoImpl extends ASBaseDaoImpl<Auction, String> implements AuctionDao
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
	
	public Auction findActiveAuctionByItemName(String itemName, String description, AuctionStatusEnum auctionStatus)
	{
		Auction auction = null;
		
		TypedQuery<Auction> query = entityManager.createNamedQuery(Auction.FIND_ACTIVE_AUCTION_BY_ITEM_NAME, Auction.class);

		query.setParameter("itemName", itemName);
		query.setParameter("description", description);
		query.setParameter("auctionStatus", auctionStatus);

		try
		{
			auction  = query.getSingleResult();
		}
		catch(NoResultException nre)
		{
			//no result Found in database.
		}

		return auction;
	}
}
