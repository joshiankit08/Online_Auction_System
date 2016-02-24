package com.auction.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.auction.dao.ASBaseDao;
import com.auction.dao.UserDao;
import com.auction.entities.Item;
import com.auction.entities.User;

@SuppressWarnings({ "restriction" })
public class UserServiceImpl extends ASBaseServiceImpl<String, User> implements UserService
{
	@Autowired
	private UserDao userDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
	public void init() throws Exception 
	{
		super.setDAO((ASBaseDao) userDao);
	}
    
	public void setUserDao(UserDao userDao) 
	{
		this.userDao = userDao;
	}
	
	public boolean validateUser(String username, String password)
	{
		return userDao.validateUser(username, password);
	}
	
	public User findByUsername(String username)
	{
		return userDao.findByUsername(username);
	}
	
	
}
