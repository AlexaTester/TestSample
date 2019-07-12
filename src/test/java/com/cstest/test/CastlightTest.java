package com.cstest.test;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cstest.util.Elementutil_HeadLess;

public class CastlightTest //extends Elementutil_HeadLess
{
	public Elementutil_HeadLess elementutil;
	//public Actions action;
	public WebElement 		element;
	 
	 @BeforeClass public void intialise() 
	 { 
		 elementutil = new Elementutil_HeadLess(); 
		 //action = new Actions(driver);
	 }
	 
	 	
	@Test
	public void elementClick() throws InterruptedException
	{
		elementutil.openUrl("aut");
		Thread.sleep(5000);
		elementutil.inputvalue("search_bar", "searchitem");
		Thread.sleep(5000);	
		elementutil.click("searchButton");
		Thread.sleep(5000);
		elementutil.click("product");
		Thread.sleep(5000);
		elementutil.contextclick();
		Thread.sleep(5000);
		elementutil.click("addtocart");
		Thread.sleep(5000);
		elementutil.closeBrowser();
		
		/*
		 * element = driver.findElement(By.xpath((properties.getProperty("product"))));
		 * System.out.println("The product xpath from prop file is :"+element.toString()
		 * ); action.contextClick(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.
		 * ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
		 * //elementutil.contextclick("product"); Thread.sleep(5000);
		 * System.out.println(driver.getWindowHandles().size());
		 * 
		 * Set<String>wins = driver.getWindowHandles();
		 * 
		 * Iterator it = wins.iterator();
		 * 
		 * while(it.hasNext()) {
		 * System.out.println("Window Opened is : "+it.next().toString());
		 * driver.switchTo().window(it.next().toString()); Thread.sleep(5000);
		 * elementutil.click("addtocart"); Thread.sleep(10000);
		 * 
		 * driver.quit(); }
		 */
	}
	
	
	
	
	
	
	
	/*
	 * public Elementutil_HeadLess elementutil;
	 * 
	 * @BeforeClass public void intialise() { elementutil = new
	 * Elementutil_HeadLess(); }
	 * 
	 * @Test public void test1() { try {
	 * elementutil.navigate_to_CreatePage_Headlessbrowser(); Thread.sleep(10000);
	 * elementutil.upload_Medical_Sheet(); Thread.sleep(10000);
	 * elementutil.upload_Dental_Sheet(); Thread.sleep(10000);
	 * elementutil.upload_Another_Sheet(); Thread.sleep(10000);
	 * elementutil.closeBrowser();
	 * 
	 * Thread.sleep(900000); // Waiting for 15 mints 10,000 mills = 10 sec
	 * 
	 * elementutil.check_the_status_Headless();
	 * 
	 * } catch (Exception e) { System.out.println(e.getMessage()); }
	 * 
	 * }
	 */}
