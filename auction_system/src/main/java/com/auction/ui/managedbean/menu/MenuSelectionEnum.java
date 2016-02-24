package com.auction.ui.managedbean.menu;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import com.auction.ui.utilities.JSFUtility;

/**
 * Menu selection enum to capture the current page parameters.
 *
 * @author Ankit Joshi
 */
@ManagedBean
@NoneScoped
public enum MenuSelectionEnum implements Serializable 
{
	/**Home page link info*/
    home("Home_Title", "homeRightPanel"),
    login("Login_Title", "login"),
    register("Register_Title", "registerUser"),
    auction_details("AuctionDetails_Title", "pages/auctionDetails"),
    offer_item("OfferItem_Title", "pages/offerItem"),
    open_bid("OpenBid_Title", "pages/openBids");
    
    public static final String PREFIX = "/content/";
    
    public static final String SUFFIX = ".xhtml";
    
    private String screenHeader;
    
    private String screenName;

    /**
     * Instantiates a new menu selection enum.
     *
     * @param screenHeader the screen header
     * @param screenName the screen name
     */
    private MenuSelectionEnum(String screenHeader, String screenName) 
    {
        this.screenHeader = screenHeader;
        this.screenName = PREFIX + screenName + SUFFIX;
    }

    /**
     * Gets the screen header.
     *
     * @return the screen header
     */
    public String getScreenHeader() 
    {
        return JSFUtility.getLocalMessageValue(screenHeader);
    }

    /**
     * Sets the screen header.
     *
     * @param screenHeader the new screen header
     */
    public void setScreenHeader(String screenHeader) 
    {
        this.screenHeader = screenHeader;
    }

    /**
     * Gets the screen name.
     *
     * @return the screen name
     */
    public String getScreenName() 
    {
        return screenName;
    }

    /**
     * Sets the screen name.
     *
     * @param screenName the new screen name
     */
    public void setScreenName(String screenName) 
    {
        this.screenName = screenName;
    }

}
