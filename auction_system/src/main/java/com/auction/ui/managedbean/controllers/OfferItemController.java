package com.auction.ui.managedbean.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.auction.entities.Auction;
import com.auction.entities.AuctionHouse;
import com.auction.entities.AuctionStatusEnum;
import com.auction.entities.Item;
import com.auction.entities.User;
import com.auction.exception.DuplicateAuctionException;
import com.auction.service.AuctionService;
import com.auction.service.ItemService;
import com.auction.service.UserService;
import com.auction.ui.managedbean.menu.MenuSelectionEnum;
import com.auction.ui.utilities.JSFUtility;

@ManagedBean(name="offerItemController")
@SessionScoped
public class OfferItemController extends BaseController
{
	private static final long serialVersionUID = 1L;

	private List<Item> items = new ArrayList<Item>();

	private ItemService itemService;

	private UserService userService;

	private AuctionService auctionService;

	private AuctionHouse auctionHouse;

	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	public OfferItemController() 
	{
		initializeSevices();

		getAllItems();
	}

	@Override
	protected void initializeSevices() 
	{
		itemService = getServiceSpringBean(ItemService.class);

		userService = getServiceSpringBean(UserService.class);

		auctionHouse = getServiceSpringBean(AuctionHouse.class);

		auctionService = getServiceSpringBean(AuctionService.class);
	}

	public void addItem() 
	{	
		Item item = new Item();
		items.add(item);
	}

	public void addItemInList(Item item)
	{
		items.add(item);
	}

	public void deleteItem(Item item)
	{
		items.remove(item);
	}

	public void save()
	{
		boolean duplicateAuctionFound = false;
		String username = (String) JSFUtility.getSession().getAttribute("username");

		User user = userService.findByUsername(username);

		List<Item> listForAuction = new ArrayList<>();
		if(user != null)
		{
			try 
			{
				for(Item item : items)
				{
					if(item.getId() == null)
					{
						Auction duplicateAuction = auctionService.findActiveAuctionByItemName(item.getName(), item.getDescription(), AuctionStatusEnum.ACTIVE);

						if(duplicateAuction != null)
						{
							duplicateAuctionFound = true;
							break;
						}

						item.setOwner(user);
						item = itemService.saveOrUpdate(item);
						listForAuction.add(item);
					}
				}

				if(duplicateAuctionFound)
				{
					resetItemList();
					JSFUtility.addErrorMessage("ErrorOccuredDuplicateAuctionFound",null);
				}
				else
				{
					getAllItems();

					executorService.submit(new AuctionTask(auctionHouse, user, listForAuction));

					JSFUtility.addSuccessMessage("item_added_successfully",null);
					JSFUtility.gotoPage(MenuSelectionEnum.open_bid);
				}
			} 
			catch (DuplicateAuctionException e) 
			{
				resetItemList();
				JSFUtility.addErrorMessage("ErrorOccuredDuplicateAuctionFound",null);
			}
			catch (Exception e) 
			{
				resetItemList();
				JSFUtility.addErrorMessage("ErrorOccuredWhileAddingItem",null);
			}
		}
		else
		{
			resetItemList();
			JSFUtility.addErrorMessage("ErrorWhileOfferingItem",null);
		}
	}

	private void resetItemList() 
	{
		items = new ArrayList<Item>();

		getAllItems();
	}

	public void cancel() 
	{
		JSFUtility.gotoPage(MenuSelectionEnum.auction_details);
	}

	public void viewOfferItemPage()
	{
		JSFUtility.gotoPage(MenuSelectionEnum.offer_item);
	}

	public void viewOpenBidPage()
	{
		JSFUtility.gotoPage(MenuSelectionEnum.open_bid);
	}

	public List<Item> getItems()
	{
		return items;
	}

	public void getAllItems()
	{
		String username = (String) JSFUtility.getSession().getAttribute("username");

		User user = userService.findByUsername(username);

		try
		{
			if(user != null)
			{
				items = itemService.getItemsOfferedBySeller(user);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

class AuctionTask implements Callable<Boolean>
{
	private AuctionHouse auctionHouse;

	private User user;

	private List<Item> listForAuction = new ArrayList<>();

	public AuctionTask(AuctionHouse auctionHouse, User user, List<Item> listForAuction) 
	{
		this.auctionHouse = auctionHouse;
		this.user = user; 
		this.listForAuction = listForAuction;
	}

	@Override
	public Boolean call() throws Exception
	{
		try 
		{
			auctionHouse.createAuction(user, listForAuction);
		} 
		catch (DuplicateAuctionException e) 
		{
			throw e;
		}
		catch (Exception e) 
		{
			throw e;
		}

		return true;
	}
}
