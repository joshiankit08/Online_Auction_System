package com.auction.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * This class represents an item that will be auctioned on each auction. One
 * auction can have only one item.
 *
 * @author Ankit Joshi
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = Item.FIND_ITEMS_OFFERED_BY_USER, query = "FROM Item WHERE Upper(username) = :username"),
	@NamedQuery(name = Item.FIND_BY_NAME, query = "FROM Item WHERE name = :name AND description = :description" )})
public class Item extends AuctionBase
{
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ITEMS_OFFERED_BY_USER = "findItemsOfferedByUser";
	
	public static final String FIND_BY_NAME = "findByName";

	@Basic
	private String name;
	
	@Basic
	private String description;
	
	@Basic
	private Double startingBid;
	
	@Basic
	private Double currentHighestBid;
	
	@ManyToOne
	private User owner;

	public Item() 
	{

	}

	public Item(String name, Double startingBid) 
	{
		this.name = name;
		
		this.startingBid = startingBid;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public User getOwner() 
	{
		return owner;
	}

	public void setOwner(User owner) 
	{
		this.owner = owner;
	}

	public Double getCurrentHighestBid() 
	{
		return currentHighestBid;
	}

	public void setCurrentHighestBid(Double currentHighestBid) 
	{
		this.currentHighestBid = currentHighestBid;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public Double getStartingBid() 
	{
		return startingBid;
	}

	public void setStartingBid(Double startingBid) 
	{
		this.startingBid = startingBid;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	@Override
	public String toString() 
	{
		return "Item [name=" + name + ", description=" + description + ", startingBid=" + startingBid
				+ ", currentHighestBid=" + currentHighestBid + ", owner=" + owner + "]";
	}
}
