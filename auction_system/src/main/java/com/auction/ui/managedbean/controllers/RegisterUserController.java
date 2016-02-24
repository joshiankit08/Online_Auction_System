package com.auction.ui.managedbean.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;

import com.auction.entities.User;
import com.auction.service.UserService;
import com.auction.ui.managedbean.menu.MenuSelectionEnum;
import com.auction.ui.utilities.JSFUtility;


@ManagedBean(name="registerUserController")
@SessionScoped
public class RegisterUserController extends BaseController
{
	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RegisterUserController.class);

	private UserService userService;

	private User user;


	public RegisterUserController() 
	{
		initializeSevices();
		
		user = new User();
	}

	@Override
	protected void initializeSevices() 
	{
		userService = getServiceSpringBean(UserService.class);	
	}

	public String save()
	{
		try 
		{
			String username = user.getUsername();

			User duplicateUser = userService.findByUsername(username);

			if(duplicateUser != null)
			{
				JSFUtility.addErrorMessage("UsernameAlreadyExistsError", username);
			}
			else
			{
				userService.saveOrUpdate(user);

				JSFUtility.addSuccessMessage("UserRegistered", user.getUsername());
				
				JSFUtility.gotoPage(MenuSelectionEnum.login);
				
				reset();
			}
		} 
		catch (Exception e) 
		{
			JSFUtility.addErrorMessage("RegisterUserError", null);
			LOGGER.error("Error in RegisterUserController.save()");
		}
		
		return null;
	}

	public void reset()
	{
		user.setEmailID(null);
		user.setFirstname(null);
		user.setLastname(null);
		user.setPassword(null);
		user.setUsername(null);
	}
	
	public User getUser() 
	{
		return user;
	}

	public void setUser(User user) 
	{
		this.user = user;
	}

}
