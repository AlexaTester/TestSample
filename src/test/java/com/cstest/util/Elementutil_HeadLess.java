package com.cstest.util;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Elementutil_HeadLess 
{
	public WebDriver 		wdriver;
	public ChromeDriver 	driver;
	public WebDriverWait 	wait;
	public Xls_Reader		xlreader;
	public int 				minimum=1,maximum=100,randomNum;
	public String 			fileName,actualValue,taskid,newLocation,originalfileAbsPath,status,expectedStatus,downloadFilepath,medicaltaskId,DentalTaskId,AnotherTaskId,Taskstatus;
	public String 			medicalTaskStatus,dentalTaskStatus,anotherTaskStatus,xlresultStatus;
	public Properties 		properties;
	public FileInputStream 	fis;
	public Robot 			robot;
	public File 			downloadDirectory,originalfile,resultFile;
	public File[] 			noOfFiles;
	public Actions			action;
	public WebElement 		element;
	 
	
	public Elementutil_HeadLess()
	{
		try 
		{
			properties 	= new Properties();
			fis 		= new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\application.properties");
			properties.load(fis);			
			robot 		= new Robot();
		} 
		catch (Exception e) 
		{			
			System.out.println(e.getMessage());
		}
				
	}
	
	public void browserSetup()
	{
		//newLocation= System.getProperty("user.dir") + File.separator + "downloadFiles";
		System.setProperty("webdriver.chrome.driver", "E:\\SeleniumWorkspace\\TestSample\\src\\test\\resources\\Exefile_Driver\\chromedriver.exe"/*System.getProperty("user.dir")+properties.getProperty("chromedriverexepath")*/);
		//Map<String, Object> prefs = new HashMap<String, Object>();
       // prefs.put("download.default_directory",newLocation);
	    //ChromeOptions options = new ChromeOptions();
	    //options.setExperimentalOption("prefs", prefs);System.out.println(options.getExperimentalOption("prefs"));
		//driver = new ChromeDriver(options);
		driver = new ChromeDriver()
		action = new Actions(driver);
	}
	
	
	
	public void contextclick(/* String locator */) throws InterruptedException
	{
		
		System.out.println(driver.getWindowHandles().size());
		
		Set<String>wins = driver.getWindowHandles();
		
		Iterator it = wins.iterator();
		
		while(it.hasNext())
		{
			System.out.println("Window Opened is : "+it.next().toString());
			driver.switchTo().window(it.next().toString());
			Thread.sleep(5000);			
		}
		
	}
	
	public void openUrl(String url)
	{		
		browserSetup();
		//headlessbrowserSetUp();
		driver.manage().window().maximize();
		driver.get(properties.getProperty(url));
		
	}
	
	public void inputvalue(String locator,String inputValue)
	{
		driver.findElement(By.xpath((properties.getProperty(locator)))).sendKeys((properties.getProperty(inputValue)));
	}
	
	public void input(String locator,String inputValue)
	{
		driver.findElement(By.xpath((properties.getProperty(locator)))).sendKeys(inputValue);
	}
	
	public void click(String locator)
	{
		driver.findElement(By.xpath((properties.getProperty(locator)))).click();		
	}
	
	public void clear(String locator)
	{
		driver.findElement(By.xpath((properties.getProperty(locator)))).clear();		
	}
	
	public void selectfromdropdown(String locator,String dropdownValue)
	{
		driver.findElement(By.xpath((properties.getProperty(locator)))).sendKeys(properties.getProperty(dropdownValue));
	}
	
	public void waitexplicitly(String locator)
	{
		wait = new WebDriverWait(driver,120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((properties.getProperty(locator)))));
	}
	
	public String verifyStatus(String locator/*,String expectedValue,String expectedValue1*/)
	{
		try 
		{
			actualValue = driver.findElement(By.xpath((properties.getProperty(locator)))).getText().toString();
			
			System.out.println("Actual Value is : "+actualValue);
			return actualValue;
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return e.getMessage();
		}
		//return expectedValue1;
	}
	
	
	public int generateRandomName()
	{
		 randomNum = minimum + (int)(Math.random() * maximum);
		 System.out.println("Genarated Random No is : "+randomNum);
		 
		 return(randomNum);
	}
	
	public void closeBrowser()
	{
		driver.quit();
	}
	
	
		
	public void closeAllDriverProcess()
	{		
		closeBrowser();		
	}
	
	
	
	
	
	
	public void navigate_to_CreatePage_Headlessbrowser()
	{
		try 
		{
			openUrl			(("application_Url"));
			waitexplicitly	("username_textbox");
			inputvalue		(("username_textbox"), ("username"));
			inputvalue		(("password_textbox"), ("password"));
			click			("submit_button");
			waitexplicitly	("task_topnavigation");			
			click			("task_topnavigation");
			waitexplicitly	("createNew_Link");
			click			("createNew_Link");			
			waitexplicitly	("task_name_textbox");			
			
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	
		
}
