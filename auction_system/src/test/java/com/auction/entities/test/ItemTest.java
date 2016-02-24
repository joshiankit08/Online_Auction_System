package com.auction.entities.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-webApplication-context.xml"})
public class ItemTest 
{
	@Test
	public void test1()
	{
		System.out.println("success");
	}
}
