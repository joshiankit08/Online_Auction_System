package com.auction.entities.test;

import java.util.Calendar;
import java.util.Date;

public class TimerTest 
{
	public static void main(String[] args)
	{
		Calendar cal = Calendar.getInstance();
		Date auctionStartDate = cal.getTime();
		
		cal.add(Calendar.MINUTE, 5);
		Date auctionEndTime = cal.getTime();
		
		long diff = auctionEndTime .getTime()- auctionStartDate.getTime();
		long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
	
        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
        System.out.println("Time in hours: " + diffHours + " hours.");
	}
}
