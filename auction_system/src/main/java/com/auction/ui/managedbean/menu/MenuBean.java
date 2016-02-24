package com.auction.ui.managedbean.menu;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Menu bean to capture the current page instance.
 *
 * @author Ankit Joshi
 * @Date 18 Nov, 2015
 */
@ManagedBean
@SessionScoped
public class MenuBean implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private MenuSelectionEnum current;

	public MenuBean() 
	{
		current = MenuSelectionEnum.home;
	}

	public MenuSelectionEnum getCurrent() 
	{
		return current;
	}

	public void setCurrent(MenuSelectionEnum current) 
	{
		this.current = current;
	}
}
