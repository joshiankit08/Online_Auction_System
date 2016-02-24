package com.auction.entities;

import javax.persistence.Entity;

@Entity
public class Seller extends User
{
	private static final long serialVersionUID = 1L;

	public Seller() 
	{

	}
	
	public Seller(String username) 
	{
		super(username);
	}
}
