package com.auction.dao;

import javax.persistence.EntityManager;

import com.auction.entities.User;

public interface UserDao  extends ASBaseDao<String, User> 
{
	public void setEntityManager(EntityManager entityManager);
	
	public boolean validateUser(String username, String password);
	
	public User findByUsername(String username);
	
}
