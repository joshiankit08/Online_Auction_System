package com.auction.ui.managedbean.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.auction.ui.managedbean.menu.MenuSelectionEnum;
import com.auction.ui.utilities.JSFUtility;

@ManagedBean(name="homeController")
@SessionScoped
public class HomeController extends BaseController
{
	private static final long serialVersionUID = 1L;

	public HomeController() 
	{
		initializeSevices();
	}

	@Override
	protected void initializeSevices() 
	{

	}

	public String viewLoginPage()
	{
		JSFUtility.gotoPage(MenuSelectionEnum.login);
		return null;
	}

	public String viewRegistrationPage()
	{
		JSFUtility.gotoPage(MenuSelectionEnum.register);
		return null;
	}

	public String viewHomePage()
	{
		if(JSFUtility.getSession().getAttribute("username") != null)
		{
			JSFUtility.gotoPage(MenuSelectionEnum.auction_details);
		}
		else
		{
			JSFUtility.gotoPage(MenuSelectionEnum.home);
		}
		return null;
	}
}
