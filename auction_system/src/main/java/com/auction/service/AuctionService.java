package com.auction.service;

import com.auction.entities.Auction;
import com.auction.entities.AuctionStatusEnum;

public interface AuctionService extends ASBaseService<String, Auction>
{
	public Auction findActiveAuctionByItemName(String itemName, String description, AuctionStatusEnum auctionStatus);
	
	//public void receiveOffer(Auction auction, Double bid, User user, Item item) throws Exception;
}
