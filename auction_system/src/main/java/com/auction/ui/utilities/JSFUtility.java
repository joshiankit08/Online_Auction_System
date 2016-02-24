package com.auction.ui.utilities;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.auction.ui.managedbean.menu.MenuBean;
import com.auction.ui.managedbean.menu.MenuSelectionEnum;

/**
 * JSFUtility methods used by UI.
 *
 * @author Ankit Joshi
 */
public final class JSFUtility
{
	public static final String BUNDLE_NAME = "res";

	/**
	 * Gets the message value for passed message id from the resource bundle.
	 *
	 * @param messageId the message id
	 * @return the local message value
	 */
	public static String getLocalMessageValue(String messageId)
	{
		FacesContext ctx = FacesContext.getCurrentInstance();

		if(ctx!=null)
		{
			ApplicationFactory factory = (ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);

			ResourceBundle bundle = factory.getApplication().getResourceBundle(ctx, BUNDLE_NAME);

			return bundle.getString(messageId);
		}
		return null;
	}


	/**
	 * Adds the success message to display on screen.
	 *
	 * @param messageId the message id
	 * @param paramValue the param value
	 */
	public static void addSuccessMessage(String messageId,String paramValue) 
	{
		FacesContext ctx = FacesContext.getCurrentInstance();

		if(ctx!=null)
		{
			ApplicationFactory factory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);

			ResourceBundle bundle = factory.getApplication().getResourceBundle(ctx, BUNDLE_NAME);

			String msg = bundle.getString(messageId);

			Object[] args = {paramValue};

			msg = MessageFormat.format(msg,args);

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg , msg);

			FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
		}
	}    

	/**
	 * Adds the error message to display on screen.
	 *
	 * @param messageId the message id
	 * @param paramValue the param value
	 */
	public static void addErrorMessage(String messageId,String paramValue,String error) 
	{
		FacesContext ctx = FacesContext.getCurrentInstance();

		if(ctx!=null)
		{
			ApplicationFactory factory = (ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);

			ResourceBundle bundle = factory.getApplication().getResourceBundle(ctx, BUNDLE_NAME);

			String msg = bundle.getString(messageId);

			Object[] args = {paramValue,error};

			msg = MessageFormat.format(msg,args); 

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);

			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
	}    

	public static void addErrorMessage(String messageId,String... paramValue) 
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		if(ctx!=null)
		{
			ApplicationFactory factory = (ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
			
			ResourceBundle bundle = factory.getApplication().getResourceBundle(ctx, BUNDLE_NAME);
			
			String msg = bundle.getString(messageId);
			
			msg = MessageFormat.format(msg,paramValue); 
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
			
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
	}

	/**
	 * Format the number in given pattern.
	 *
	 * @param number the number
	 * @param pattern the pattern
	 * @return the string
	 */
	public static String formatNumberInPattern(int number,String pattern)
	{
		NumberFormat formatter = new DecimalFormat(pattern);

		String formattedNumber = formatter.format(number);

		return formattedNumber;
	}

	/**
	 * Find bean.
	 *
	 * @param <T> the generic type
	 * @param beanName the bean name
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public static <T> T findBean(String beanName) 
	{
		FacesContext context = FacesContext.getCurrentInstance();

		return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}


	/**
	 * Adds the faces message.
	 *
	 * @param facesMessage the faces message
	 */
	public static void addFacesMessage(FacesMessage facesMessage) 
	{
		FacesContext ctx = FacesContext.getCurrentInstance();

		if(ctx!=null)
		{
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}    
	}

	public static void gotoPage(MenuSelectionEnum menuSelectionEnum)
	{
		MenuBean bean= findBean("menuBean");

		bean.setCurrent(menuSelectionEnum);
	}

	public static HttpSession getSession() 
	{
		return (HttpSession)
				FacesContext.
				getCurrentInstance().
				getExternalContext().
				getSession(true);
	}
}
