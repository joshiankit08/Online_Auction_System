package com.auction.ui.managedbean.controllers;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

public abstract class BaseController implements Serializable 
{

	private static final long serialVersionUID = 1L;

	private static WebApplicationContext ctx;

	static
	{
		ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	}

	/**
	 * Return the bean instance that uniquely matches the given object type, if any.
	 * @param requiredType type the bean must match; can be an interface or superclass.
	 * {@code null} is disallowed.
	 */
	protected <T> T getServiceSpringBean(Class<T> requiredType)
	{
		return ctx.getBean(requiredType);
	}

	protected abstract void initializeSevices();
}

