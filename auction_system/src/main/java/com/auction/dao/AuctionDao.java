package com.auction.dao;

import javax.persistence.EntityManager;

import com.auction.entities.Auction;
import com.auction.entities.AuctionStatusEnum;

public interface AuctionDao extends ASBaseDao<Auction, String> 
{
	public void setEntityManager(EntityManager entityManager);
	
	public Auction findActiveAuctionByItemName(String itemName, String description, AuctionStatusEnum auctionStatus);
}
