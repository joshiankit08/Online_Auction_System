package com.auction.service;

import com.auction.entities.User;

public interface UserService extends ASBaseService<String, User>
{
	public boolean validateUser(String username, String password);
	
	public User findByUsername(String username);
}
