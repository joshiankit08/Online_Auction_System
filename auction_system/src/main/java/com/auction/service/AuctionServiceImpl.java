package com.auction.service;


import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.auction.dao.ASBaseDao;
import com.auction.dao.AuctionDao;
import com.auction.entities.Auction;
import com.auction.entities.AuctionStatusEnum;

@SuppressWarnings("restriction")
public class AuctionServiceImpl extends ASBaseServiceImpl<String, Auction> implements AuctionService
{
	@Autowired
	private AuctionDao auctionDao;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ItemService itemService;
	
	@SuppressWarnings("restriction")
	@PostConstruct
	public void init() throws Exception 
	{
		super.setDAO((ASBaseDao) auctionDao);
	}
	
	public void setEntityManagerOnDao(EntityManager entityManager)
    {
		auctionDao.setEntityManager(entityManager);
    }
    
	public void setAuctionDao(AuctionDao auctionDao) 
	{
		this.auctionDao = auctionDao;
	}
	
	public Auction findActiveAuctionByItemName(String itemName, String description, AuctionStatusEnum auctionStatus)
	{
		return auctionDao.findActiveAuctionByItemName(itemName, description, auctionStatus);
	}
	
//	public void receiveOffer(Auction auction, Double bid, User user, Item item) throws Exception
//	{
//		try
//		{
//			String msg = "User " + user.getUsername() + " made a bid of " + bid + " dollars for " + item.getName();
//
//			System.out.println(msg);
//
//			if (bid > auction.getCurrentBid()) 
//			{
//				user.setUserTypeEnum(UserTypeEnum.BIDDER);
//				user = userService.saveOrUpdate(user);
//				
//				item.setCurrentHighestBid(bid);
//				item = itemService.saveOrUpdate(item);
//				
//				auction.setCurrentBid(bid);
//				auction.setCurrentHighestBidder(user);
//				auction.setItem(item);
//				
//				auction = auctionDao.merge(auction);
//				//auction = auctionDao.flush(auction);
//
//				System.out.println("The Offer is better than the current!");
//			} 
//			else 
//			{
//				throw new Exception();
//			}
//		}
//		catch(Exception e)
//		{
//			throw new Exception();
//		}
//	}

}
