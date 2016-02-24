package com.auction.ui.managedbean.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.auction.entities.Auction;
import com.auction.entities.AuctionHouse;
import com.auction.entities.AuctionStatusEnum;
import com.auction.entities.Item;
import com.auction.entities.User;
import com.auction.entities.UserTypeEnum;
import com.auction.exception.BidIsLessThanCurrentBidException;
import com.auction.exception.OwnerCanNotBidException;
import com.auction.service.AuctionService;
import com.auction.service.ItemService;
import com.auction.service.UserService;
import com.auction.ui.managedbean.menu.MenuSelectionEnum;
import com.auction.ui.utilities.JSFUtility;

@ManagedBean(name="openBidController")
@SessionScoped
public class OpenBidController extends BaseController
{
	private static final long serialVersionUID = 1L;

	private List<Auction> auctions = new ArrayList<Auction>();

	private AuctionService auctionService;

	private ItemService itemService;

	private UserService userService;

	private AuctionHouse auctionHouse;

	public OpenBidController() 
	{
		initializeSevices();

		getAllAuctions();
	}

	@Override
	protected void initializeSevices() 
	{
		auctionService = getServiceSpringBean(AuctionService.class);

		itemService = getServiceSpringBean(ItemService.class);

		userService = getServiceSpringBean(UserService.class);

		auctionHouse = getServiceSpringBean(AuctionHouse.class);
	}

	public void cancel() 
	{
		JSFUtility.gotoPage(MenuSelectionEnum.auction_details);
	}

	public void viewOpenBidPage()
	{
		JSFUtility.gotoPage(MenuSelectionEnum.open_bid);
	}

	public List<Auction> getAuctions()
	{
		//getAllAuctions();
		return auctions;
	}

	public void getAllAuctions()
	{
		try
		{
			auctions = auctionService.findAll();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public boolean isUserObserver()
	{
		boolean isObserver = false;

		String username = (String) JSFUtility.getSession().getAttribute("username");
		User user = userService.findByUsername(username);

		if(user.getUserTypeEnum().equals(UserTypeEnum.OBSERVER))
		{
			isObserver = true;
		}

		return isObserver;
	}

	public void placeBid(Auction auction)
	{
		String username = (String) JSFUtility.getSession().getAttribute("username");
		User user = userService.findByUsername(username);

		if(user != null)
		{
			try 
			{
				if(!auction.getAuctionStatus().equals(AuctionStatusEnum.CLOSED))
				{
					Double bid = auction.getPlaceBid();

					String itemName = auction.getItemName();
					String description = auction.getDescription();
					
					Item item = itemService.findByName(itemName, description);

					auctionHouse.placeBid(bid, item, user);
					JSFUtility.addSuccessMessage("YourBidIsPlaced", null);
				}
				else
				{
					JSFUtility.addErrorMessage("AuctionAlreadyClosed", null);
				}
			}
			catch(BidIsLessThanCurrentBidException e)
			{
				JSFUtility.addErrorMessage("BidIsLessThanCurrentBid", null);
				e.printStackTrace();
			}
			catch(OwnerCanNotBidException e)
			{
				JSFUtility.addErrorMessage("SellerObserverCanNotBid", null);
				e.printStackTrace();
			}
			catch (Exception e) 
			{
				JSFUtility.addErrorMessage("ErrorOccurredWhilePlacingBid", null);
				e.printStackTrace();
			}
		}
		else
		{
			JSFUtility.addErrorMessage("ErrorOccuredWhileSubmittingBid", null);
		}
	}


}
