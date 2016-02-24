package com.auction.service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.auction.dao.ASBaseDao;
import com.auction.dao.SellerDao;
import com.auction.entities.Seller;

@SuppressWarnings("restriction")
public class SellerServiceImpl extends ASBaseServiceImpl<String, Seller> implements SellerService
{
	@Autowired
	private SellerDao sellerDao;
	
	@SuppressWarnings("restriction")
	@PostConstruct
	public void init() throws Exception 
	{
		super.setDAO((ASBaseDao) sellerDao);
	}
	
//	public List<Item> getItemsOfferedBySeller(Seller seller)
//	{
//		return sellerDao.getItemsOfferedBySeller(seller);
//	}
	
	public void setEntityManagerOnDao(EntityManager entityManager)
    {
		sellerDao.setEntityManager(entityManager);
    }
    
	public void setSellerDao(SellerDao sellerDao) 
	{
		this.sellerDao = sellerDao;
	}

}
