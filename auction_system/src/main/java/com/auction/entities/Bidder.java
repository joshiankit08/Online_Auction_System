package com.auction.entities;

import javax.persistence.Entity;

@Entity
public class Bidder extends User 
{
	private static final long serialVersionUID = 1L;

	public Bidder() 
	{

	}
	
	public Bidder(String username) 
	{
		super(username);
	}
}
