package com.auction.ui.managedbean.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.auction.service.UserService;
import com.auction.ui.managedbean.menu.MenuSelectionEnum;
import com.auction.ui.utilities.JSFUtility;

@ManagedBean(name="loginController")
@SessionScoped
public class LoginController extends BaseController
{
	private static final long serialVersionUID = 1L;

	private UserService userService;

	private String password;
	private String message;
	private String username;

	public LoginController() 
	{
		initializeSevices();
	}
	
	@Override
	protected void initializeSevices()
	{
		userService = getServiceSpringBean(UserService.class);	
	}
	
	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getMessage() 
	{
		return message;
	}

	public void setMessage(String message) 
	{
		this.message = message;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	/**
	 * validate login
	 * @return
	 */
	public String validateUsernamePassword() 
	{
		boolean valid = userService.validateUser(username, password);
		
		if (valid) 
		{
			HttpSession session = JSFUtility.getSession();
			session.setAttribute("username", username);

			JSFUtility.addSuccessMessage("config_login_success",null);
			
			username = null;
			
			password = null;

			JSFUtility.gotoPage(MenuSelectionEnum.auction_details);
		} 
		else 
		{
			JSFUtility.addErrorMessage("config_login_failure", null);
		}
		
		return null;
	}

	public boolean isUserLoggedIn()
	{
		boolean userLoggedIn = false;
		
		HttpSession session = JSFUtility.getSession() ;
		
		if(session != null && session.getAttribute("username") != null)
		{
			userLoggedIn = true;
		}
		
		return userLoggedIn;
	}
	
	/**
	 * logout event, invalidate session
	 * @return
	 */
	public String logout() 
	{
		HttpSession session = JSFUtility.getSession();
		session.invalidate();
		JSFUtility.gotoPage(MenuSelectionEnum.home);
		return null;
	}
}
