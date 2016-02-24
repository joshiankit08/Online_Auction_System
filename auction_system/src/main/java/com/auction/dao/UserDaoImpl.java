package com.auction.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.auction.entities.User;

public class UserDaoImpl extends ASBaseDaoImpl<String, User> implements UserDao
{
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@PersistenceContext(unitName = "ASPersistenceUnit")
	private EntityManager entityManager;

	@PostConstruct
	public void init()
	{
		super.setEntityManagerFactory(entityManagerFactory);
		super.setEntityManager(entityManager);
	}

	public boolean validateUser(String username, String password)
	{
		boolean userIsValid = false;

		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_USERNAME_AND_PASSWORD, User.class);

		query.setParameter("username", username);
		query.setParameter("password", password);

		try
		{
			query.getSingleResult();
			
			userIsValid = true;
		}
		catch(NoResultException nre)
		{
			//no result Found in database.
		}

		return userIsValid;

	}

	public User findByUsername(String username)
	{
		User user = null;

		TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class);

		query.setParameter("username", username);

		try
		{
			user  = query.getSingleResult();
		}
		catch(NoResultException nre)
		{
			//no result Found in database.
		}

		return user;
	}
	
	
}
