package com.auction.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * This class represents a user in our world who can be either an auctioneer or
 * a buyer.
 *
 * @author Ankit Joshi
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = User.FIND_BY_USERNAME, query = "FROM User WHERE Upper(username) = :username"),
	@NamedQuery(name = User.FIND_BY_USERNAME_AND_PASSWORD, query = "FROM User WHERE Upper(username) = :username AND password = :password")})

public class User extends AuctionBase
{
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_BY_USERNAME = "findByUserName";
	
	public static final String FIND_BY_USERNAME_AND_PASSWORD = "findByUsernameAndPassword";

	@Basic
	private String firstname;

	@Basic
	private String lastname;

	@Basic
	private String emailID;

	@Basic
	private String username;

	@Basic
	private String password;

	@Enumerated(EnumType.STRING)
	private UserTypeEnum userTypeEnum = UserTypeEnum.OBSERVER;

	public User() 
	{
	
	}
	
	public User(String username) 
	{
		this.username = username;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getEmailID() 
	{
		return emailID;
	}

	public void setEmailID(String emailID) 
	{
		this.emailID = emailID;
	}

	public UserTypeEnum getUserTypeEnum() 
	{
		return userTypeEnum;
	}

	public void setUserTypeEnum(UserTypeEnum userTypeEnum) 
	{
		this.userTypeEnum = userTypeEnum;
	}

	public String getFirstname() 
	{
		return firstname;
	}

	public void setFirstname(String firstname) 
	{
		this.firstname = firstname;
	}

	public String getLastname() 
	{
		return lastname;
	}

	public void setLastname(String lastname) 
	{
		this.lastname = lastname;
	}
	

	@Override
	public String toString() 
	{
		return "User [firstname=" + firstname + ", lastname=" + lastname + ", emailID=" + emailID + ", username="
				+ username + ", password=" + password + ", userTypeEnum=" + userTypeEnum + "]";
	}
}
