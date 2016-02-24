package com.auction.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.auction.exception.BidIsLessThanCurrentBidException;
import com.auction.exception.DuplicateAuctionException;
import com.auction.exception.OwnerCanNotBidException;
import com.auction.service.AuctionService;
import com.auction.service.ItemService;
import com.auction.service.UserService;

/**
 * This class represents the auction house that handles everything that have to
 * do with auctions. Originally this class represented the whole system, it kept
 * lists of the users and items as well but those were later moved to the World
 * class. Now this class only keeps a list of all the auctions. The split
 * between this class and World was first to divide the load to different
 * classes then also because it makes more sense realistically and lastly
 * because it gives the program better architecture that can aid in a possible
 * future expansion of it (ex. We have many auction houses and not just one)
 *
 * @author Ankit Joshi
 */
public class AuctionHouse 
{
	private static final int AUCTION_DURATION = 5;

	private Map<String, Auction> auctions = new HashMap<String, Auction>();

	@Autowired
	private ItemService itemService;

	@Autowired
	private AuctionService auctionService;

	@Autowired
	private UserService userService;

	private Timer timer = new Timer();

	@PreDestroy
	protected void destroy()
	{
		timer.cancel();
		timer.purge();
	}

	@PostConstruct
	public void init() throws Exception
	{
		List<Auction> auctionList = auctionService.findAll();

		int auctionRetryCount = 1;

		for(Auction auction : auctionList)
		{
			if(auction.getAuctionStatus().equals(AuctionStatusEnum.ACTIVE))
			{
				Calendar cal = Calendar.getInstance(); 
				Date auctionStartDate = cal.getTime();

				cal.add(Calendar.MINUTE, AUCTION_DURATION);
				Date auctionEndTime = cal.getTime();

				auction.setAuctionStartDate(auctionStartDate);
				auction.setAuctionEndDate(auctionEndTime);
				auction = auctionService.saveOrUpdate(auction);
				timer.schedule(new CloseAuctionTask(auction, auctionService, auctionRetryCount), auctionEndTime);
			}
		}
	}


	/**
	 * The constructor merely initializes the list of the auctions that this
	 * class keeps track.
	 */
	public AuctionHouse() 
	{

	}

	public Boolean createAuction(User user, List<Item> itemListForAuction) throws Exception
	{
		for (Item item : itemListForAuction) 
		{
			startAuction(item);
		}

		return true;
	}

	private void startAuction(Item item) throws Exception 
	{
		Auction duplicateAuction = auctionService.findActiveAuctionByItemName(item.getName(), item.getDescription(), AuctionStatusEnum.ACTIVE);

		if(duplicateAuction == null)
		{
			int auctionRetryCount = 1;

			Auction auction = new Auction(item, item.getOwner(), item.getStartingBid(), item.getStartingBid());

			Calendar cal = Calendar.getInstance(); 
			Date auctionStartDate = cal.getTime();
			auction.setAuctionStartDate(auctionStartDate);

			cal.add(Calendar.MINUTE, AUCTION_DURATION);
			Date auctionEndTime = cal.getTime();

			auction.setOwnerName(item.getOwner().getUsername());
			auction.setItemName(item.getName());
			auction.setDescription(item.getDescription());
			auction.setAuctionEndDate(auctionEndTime);

			User seller = item.getOwner();
			seller.setUserTypeEnum(UserTypeEnum.SELLER);
			seller = userService.saveOrUpdate(seller);

			auction.setOwner(seller);
			auction = auctionService.saveOrUpdate(auction);

			timer.schedule(new CloseAuctionTask(auction, auctionService, auctionRetryCount), auctionEndTime);
		}
		else
		{
			throw new DuplicateAuctionException();
		}
	}

	/**
	 * This method allows the customer to place a bid for a certain auction of
	 * type Bid. The exercise assumes that only customers can place bids. This
	 * method is supposed to be used only for Bid auctions.
	 *
	 * @param bid The bid price
	 * @param auction The auction that the user wants to bid on
	 * @param user The user
	 * @throws Exception 
	 */
	public void placeBid(Double bid, Item item, User bidder) throws Exception 
	{
		try
		{
			Auction currentAuction = auctionService.findActiveAuctionByItemName(item.getName(), item.getDescription(), AuctionStatusEnum.ACTIVE);

			if(currentAuction != null)
			{
				String ownername = item.getOwner().getUsername();

				String biddernme = bidder.getUsername();

				if (!ownername.equals(biddernme)) 
				{
					receiveOffer(currentAuction, bid, bidder, item);
				} 
				else 
				{
					throw new OwnerCanNotBidException();
				}
			}
			else
			{
				throw new Exception();
			}
		}
		catch(BidIsLessThanCurrentBidException e)
		{
			throw e;
		}
		catch(OwnerCanNotBidException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	public void receiveOffer(Auction auction, Double bid, User user, Item item) throws Exception
	{
		try
		{
			String msg = "User " + user.getUsername() + " made a bid of " + bid + " dollars for " + item.getName();

			System.out.println(msg);

			if (bid > auction.getCurrentBid()) 
			{
				user.setUserTypeEnum(UserTypeEnum.BIDDER);
				user = userService.saveOrUpdate(user);

				item.setCurrentHighestBid(bid);
				item = itemService.saveOrUpdate(item);

				auction.setCurrentBid(bid);
				auction.setCurrentHighestBidder(user);
				auction.setPlaceBid(bid);
				auction.setItem(item);
				auction.setFinalPrice(bid);

				auction = auctionService.saveOrUpdate(auction);

				System.out.println("The Offer is better than the current!");
			} 
			else 
			{
				throw new BidIsLessThanCurrentBidException();
			}
		}
		catch(BidIsLessThanCurrentBidException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	/**
	 * Returns a list of the auctions tracked by this auction house.
	 *
	 * @return An ArrayList indicating the list of the auctions of this auction
	 * house
	 */
	public Map<String, Auction> getAuctions() 
	{
		return auctions;
	}

	class CloseAuctionTask extends TimerTask
	{
		private Auction auction;

		private AuctionService auctionService;

		private int auctionRetryCount;

		public CloseAuctionTask(Auction auction, AuctionService auctionService, int auctionRetryCount) 
		{
			this.auction = auction;
			this.auctionService = auctionService;
			this.auctionRetryCount = auctionRetryCount;
		}

		@Override
		public void run()
		{
			auction = auctionService.findActiveAuctionByItemName(auction.getItemName(), auction.getDescription(), AuctionStatusEnum.ACTIVE);

			if(auction.getCurrentHighestBidder() == null && auctionRetryCount < 2)
			{
				Calendar cal = Calendar.getInstance(); 
				cal.add(Calendar.MINUTE, AUCTION_DURATION);
				Date auctionEndTime = cal.getTime();

				auctionRetryCount++;
				timer.schedule(new CloseAuctionTask(auction, auctionService, auctionRetryCount), auctionEndTime);

				auction.setAuctionEndDate(auctionEndTime);
			}
			else
			{
				if(auction.getCurrentHighestBidder() == null)
				{
					auction.setAuctionStatus(AuctionStatusEnum.CLOSED);
				}
				else
				{
					auction.setWinner(auction.getCurrentHighestBidder());
					auction.setWinnerName(auction.getCurrentHighestBidder().getUsername());
					auction.setAuctionStatus(AuctionStatusEnum.CLOSED);
					auction.setFinalPrice(auction.getCurrentBid());
				}
			}

			try 
			{
				auctionService.saveOrUpdate(auction);
			} 
			catch (Exception e) 
			{
				System.out.println("Exception occurred while closing the Auction: " + auction);
			}
		}
	}
}


