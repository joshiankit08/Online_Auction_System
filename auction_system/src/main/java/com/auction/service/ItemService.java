package com.auction.service;

import java.util.List;

import com.auction.entities.Item;
import com.auction.entities.User;

public interface ItemService extends ASBaseService<Long, Item>
{
	public List<Item> getItemsOfferedBySeller(User user);
	
	public Item findByName(String name, String description);
}
