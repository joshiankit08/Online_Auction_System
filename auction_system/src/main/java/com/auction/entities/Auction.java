package com.auction.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represents an Auction in the system.
 *
 * @author Ankit Joshi
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = Auction.FIND_ACTIVE_AUCTION_BY_ITEM_NAME, query = "FROM Auction WHERE (itemName = :itemName AND description = :description) AND auctionStatus = :auctionStatus")})
public class Auction extends AuctionBase
{

	public static final String FIND_ACTIVE_AUCTION_BY_ITEM_NAME = "findActiveAuctionByItemName";

	private static final long serialVersionUID = 1L;

	@OneToOne
	private Item item;

	@OneToOne
	private User owner;

	@OneToOne
	private User currentHighestBidder;

	@OneToOne
	private User winner;

	@Basic
	private String itemName;

	@Basic
	private String description;

	@Basic
	private Double currentBid;

	@Basic
	private Double placeBid;

	@Basic
	private Double finalPrice;

	@Basic 
	private String ownerName;

	@Basic
	private String winnerName;

	@Enumerated(EnumType.STRING)
	private AuctionStatusEnum auctionStatus;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date auctionStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date auctionEndDate;


	public Auction() 
	{

	}

	/**
	 * This basic constructor creates a new Auction given basic info: the title,
	 * the item auctioned and the user the creates it. We can possibly create
	 * more constructors that allow to input more information directly, like the
	 * description without having to call the setter method later like in the
	 * case of the description field.
	 *
	 * @param title The title of the auction. this is conveniently set to be the
	 * same as the name of the item when we create auctions but it can also
	 * differ.
	 * @param item The item being auctioned
	 * @param owner The user that creates the auction and also the owner of the
	 * item (and the auction)
	 */
	public Auction(Item item, User owner, Double currentBid, Double finalPrice) 
	{
		this.item = item;
		this.owner = owner;
		this.creationDate = new Date();
		this.auctionStatus = AuctionStatusEnum.ACTIVE;
		this.currentBid = currentBid;
		this.finalPrice = finalPrice;
	}

	/**
	 * This method checks if the auction has a winner. A winner is set when the
	 * auction expires and there is a highest bidder or when a customer
	 * instantly buys an auction with a steady price aka Buyout.
	 *
	 * @return True or false depending if it has a winner
	 */
	public boolean hasWinner() 
	{
		boolean b = false;
		if (this.winner != null) 
		{
			b = true;
		}
		return b;
	}

	public User getOwner() 
	{
		return owner;
	}

	public void setOwner(User owner) 
	{
		this.owner = owner;
	}

	public Item getItem() 
	{
		return item;
	}

	public void setItem(Item item) 
	{
		this.item = item;
	}

	public Double getFinalPrice() 
	{
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) 
	{
		this.finalPrice = finalPrice;
	}

	public User getWinner() 
	{
		return winner;
	}

	public void setWinner(User winner) 
	{
		this.winner = winner;
	}

	public Date getCreationDate() 
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate) 
	{
		this.creationDate = creationDate;
	}

	public Double getCurrentBid() 
	{
		return currentBid;
	}

	public void setCurrentBid(Double currentBid) 
	{
		this.currentBid = currentBid;
	}

	public User getCurrentHighestBidder() 
	{
		return currentHighestBidder;
	}

	public void setCurrentHighestBidder(User currentHighestBidder) 
	{
		this.currentHighestBidder = currentHighestBidder;
	}

	public Date getAuctionEndDate() 
	{
		return auctionEndDate;
	}

	public void setAuctionEndDate(Date auctionEndDate) 
	{
		this.auctionEndDate = auctionEndDate;
	}

	public String getItemName() 
	{
		return itemName;
	}

	public void setItemName(String itemName) 
	{
		this.itemName = itemName;
	}

	public AuctionStatusEnum getAuctionStatus() 
	{
		return auctionStatus;
	}

	public void setAuctionStatus(AuctionStatusEnum auctionStatus) 
	{
		this.auctionStatus = auctionStatus;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getWinnerName() 
	{
		return winnerName;
	}

	public void setWinnerName(String winnerName) 
	{
		this.winnerName = winnerName;
	}

	public String getOwnerName() 
	{
		return ownerName;
	}

	public void setOwnerName(String ownerName) 
	{
		this.ownerName = ownerName;
	}

	public Double getPlaceBid() 
	{
		if(placeBid == null)
		{
			placeBid = currentBid;
		}
		return placeBid;
	}

	public void setPlaceBid(Double placeBid) 
	{
		this.placeBid = placeBid;
	}

	public Date getAuctionStartDate() 
	{
		return auctionStartDate;
	}

	public void setAuctionStartDate(Date auctionStartDate) 
	{
		this.auctionStartDate = auctionStartDate;
	}

	@Override
	public String toString() 
	{
		return "Auction [item=" + item + ", owner=" + owner + ", currentHighestBidder=" + currentHighestBidder
				+ ", winner=" + winner + ", itemName=" + itemName + ", description=" + description + ", currentBid="
				+ currentBid + ", finalPrice=" + finalPrice + ", auctionStatus=" + auctionStatus + ", creationDate="
				+ creationDate + ", auctionEndDate=" + auctionEndDate + "]";
	}

}
