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
		newLocation= System.getProperty("user.dir") + File.separator + "downloadFiles";
		System.setProperty("webdriver.chrome.driver", "E:\\SeleniumWorkspace\\TestSample\\src\\test\\resources\\Exefile_Driver\\chromedriver.exe"/*System.getProperty("user.dir")+properties.getProperty("chromedriverexepath")*/);
		Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory",newLocation);
	    ChromeOptions options = new ChromeOptions();
	    options.setExperimentalOption("prefs", prefs);System.out.println(options.getExperimentalOption("prefs"));
		driver = new ChromeDriver(options);
		action = new Actions(driver);
	}
	
	public void headlessbrowserSetUp()
	{
		try
		{
			//System.setProperty("webdriver.chrome.driver", "C:\\NewWorkspace_Selenium\\Caslight_Project\\src\\test\\resources\\DriversExeFiles\\chromedriver.exe");
			//String downloadFilepath = "C:\\NewWorkspace_Selenium\\TestProject\\downloadFiles\\";
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+properties.getProperty("chromedriverexepath"));
			downloadFilepath= System.getProperty("user.dir") + File.separator + "downloadFiles";
			HashMap<String, Object> chromePreferences = new HashMap<String, Object>();
	        chromePreferences.put("profile.default_content_settings.popups", 0);
	        chromePreferences.put("download.prompt_for_download", "false");
	        chromePreferences.put("download.default_directory", downloadFilepath);
	        ChromeOptions chromeOptions = new ChromeOptions();
	        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
	        chromeOptions.addArguments("start-maximized");
	        chromeOptions.addArguments("disable-infobars");
	        //HEADLESS CHROME
	        chromeOptions.addArguments("headless");
	
	        chromeOptions.setExperimentalOption("prefs", chromePreferences);
	        DesiredCapabilities cap = DesiredCapabilities.chrome();
	        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
	
	        ChromeDriverService driverService = ChromeDriverService.createDefaultService();
	        driver = new ChromeDriver(driverService, chromeOptions);
	
	        Map<String, Object> commandParams = new HashMap<>();
	        commandParams.put("cmd", "Page.setDownloadBehavior");
	        Map<String, String> params = new HashMap<>();
	        params.put("behavior", "allow");
	        params.put("downloadPath", downloadFilepath);
	        commandParams.put("params", params);
	        ObjectMapper objectMapper = new ObjectMapper();
	        HttpClient httpClient = HttpClientBuilder.create().build();
	        String command = objectMapper.writeValueAsString(commandParams);
	        String u = driverService.getUrl().toString() + "/session/" + driver.getSessionId() + "/chromium/send_command";
	        HttpPost request = new HttpPost(u);
	        request.addHeader("content-type", "application/json");
	        request.setEntity(new StringEntity(command));	        
	      
		    httpClient.execute(request);
		    //Continue using the driver for automation  
		    driver.manage().window().maximize();
		}		        
        catch (Exception e) 
        {
           System.out.println(e.getMessage());
        }

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
	
	public void getTheId(String locator)
	{
		taskid = driver.findElement(By.xpath((properties.getProperty(locator)))).getText().toString();
		System.out.println("Gathered Id from Method is : "+taskid);
	}
	
	public void clickDownLoadsLink(String locator)
	{
		// This will downloads the file to specific directotry instead of Default Downloads Folder
        driver.findElement(By.xpath((properties.getProperty(locator)))).click();
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
	
	public void winiumCode()
	{		
		
		try
		{
			robot.setAutoDelay(5000);
			//String filepath = properties.getProperty("medicalUpload_Sheet");
			String filepath = properties.getProperty("dental_Sheet");
			
			StringSelection stringselection = new StringSelection(filepath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
			robot.setAutoDelay(2000);
			
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			robot.setAutoDelay(2000);
			
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		 
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
		
	public void closeAllDriverProcess()
	{		
		closeBrowser();		
	}
	
	public void rename_ResultFile()
	{
		downloadDirectory	= 	new File(newLocation);
		noOfFiles 			= 	downloadDirectory.listFiles((dir1, name) -> name.startsWith("ExcelTask") && name.endsWith(".xlsx"));
		fileName			= 	noOfFiles[0].getName().toString();	
		originalfileAbsPath	= 	noOfFiles[0].getAbsoluteFile().toString();									
		
		System.out.println("File Name Only : "+fileName);
		System.out.println("Abs Path Only : "+originalfile);
		
		originalfile = new File(originalfileAbsPath);
		resultFile	 = new File(newLocation+"\\"+"Medical_Count_Result_Sheet.xlsx");
		
		 if(originalfile.renameTo(resultFile))
		 {
	            System.out.println("File rename success");	        
		 }
		 else
		 {
			 System.out.println("File rename failed");
	     }
		
		
	}
	
	public void xl_Validation() throws InterruptedException
	{
		xlreader = new Xls_Reader(resultFile.getAbsolutePath());
		
		for(int i=2; i<=xlreader.getRowCount("Report");i++)
		{
			if((xlreader.getCellData("Report", "Validation", i).equalsIgnoreCase("MISMATCH")))
			{
				System.out.println("There is an Mis Match at "+ i +" : Row, so deleting the downloaded File and Repeating the same Process");
				
				for(File file: downloadDirectory.listFiles()) 
				    if (!file.isDirectory()) 
				        file.delete();
				Thread.sleep(10000);
				
				repeatUpload();
				
			}
			else if((xlreader.getCellData("Report", "Validation", i).equalsIgnoreCase("MATCH")))
			{
				System.out.println("There is no Mis Match at All the Row");
			}
			
			System.out.println("The Exact Values from Validation Coulumn are : "+ xlreader.getCellData("Report", "Validation", i));
			
		}
		
			
		
	}
	// xlresultStatus
	
	public void uploadSheet() 
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
			inputvalue		("task_name_textbox", "taskname");
			inputvalue		(("project_selectbox"), ("project"));
			inputvalue		("automation_selectbox", "automation");
			Thread.sleep(5000);
			inputvalue		("targetenvironment_selectbox", "targetenvironment");
			//inputvalue		("dbupdate_selectbox", "dbupdate");
			Thread.sleep(5000);
			click			("input_file");			
			
			Thread.sleep(5000);
			winiumCode();
			Thread.sleep(5000);
			click			("create_button");
			Thread.sleep(5000);
			getTheId		("task_id");
			System.out.println("Gathered TaskID From Execution is : "+taskid);
			Thread.sleep(5000);
			closeBrowser();	
			Thread.sleep(5000);
			//Thread.sleep(60000);
			
			/*openUrl			(("application_Url"));
			waitexplicitly	("username_textbox");
			inputvalue		(("username_textbox"), ("username"));
			inputvalue		(("password_textbox"), ("password"));
			click			("submit_button");
			waitexplicitly	("task_topnavigation");		
			click			("task_topnavigation");
			input			(("taskid_searchBar"), (taskid));
			click			("filter_button");
			Thread.sleep(5000);
			
						
			if(actualValue.equalsIgnoreCase("Completed"))
			{
				System.out.println("In side Completed Loop");
				click				("task_id_from_Table"); // Click on the Task Id to go inside
				waitexplicitly		("download_resultFile");
				clickDownLoadsLink	("download_resultFile"); // Click on the Downloads Link
				Thread.sleep(5000);
				rename_ResultFile();
				xl_Validation();
				status = "Pass";
				//return status;
			}
			else if(actualValue.equalsIgnoreCase("Processing"))
			{
				System.out.println("In side Processing Loop");
				click("logout_button"); 
				status = "Wait";
				//return status; Processing
			}
		*/	//return "Pass";
		
			
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			status = e.getMessage();
			System.out.println(e.getMessage());
		}
	}
	
	public void task_check()
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
			input			(("taskid_searchBar"), (taskid));
			click			("filter_button");
			Thread.sleep(5000);
			
			verifyStatus("task_status");
			
			if(actualValue.equalsIgnoreCase("Completed"))
			{
				System.out.println("In side Completed Loop");
				
				click				("task_id_from_Table"); // Click on the Task Id to go inside
				waitexplicitly		("download_resultFile");
				clickDownLoadsLink	("download_resultFile"); // Click on the Downloads Link
				Thread.sleep(5000);
				click("logout_button");
				//closeBrowser();
				rename_ResultFile();
				xl_Validation();
				status = "Pass";
				closeBrowser();
				//return status;
			}
			else if(actualValue.equalsIgnoreCase("Processing"))
			{
				System.out.println("In side Processing Loop");
				Thread.sleep(5000);
				status = "Wait";
				click("logout_button"); 
				closeBrowser();
				
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			//closeBrowser();
		}
	
	}
	/*public String check_the_Task_Status()
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
			inputvalue		(("taskid_searchBar"), (taskid));
			click			("filter_button");
			Thread.sleep(5000);
			
			if(verifyStatus("task_status", "task_status_Completed_Text"))
			{
				click				("task_id_from_Table"); // Click on the Task Id to go inside
				waitexplicitly		("download_resultFile");
				clickDownLoadsLink	("download_resultFile"); // Click on the Downloads Link
				Thread.sleep(5000);
				rename_ResultFile();
				xl_Validation();
				status = "Pass";
				return status;
			}
			else
			{
				click("logout_button"); 
				status = "Wait";
				return status;
			}
			//return "Pass";
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			status = e.getMessage();
			return status;
		}
		
		
	}*/
	
	public void repeatUpload()
	{
		uploadSheet();
		task_check();
		//check_the_Task_Status();
	}

	
	//******************************
	
	
	public void xl_Validation_headless() throws InterruptedException
	{
		xlreader = new Xls_Reader(resultFile.getAbsolutePath());
		
		for(int i=2; i<=xlreader.getRowCount("Report");i++)
		{
			if((xlreader.getCellData("Report", "Validation", i).equalsIgnoreCase("MISMATCH")))
			{
				System.out.println("There is an Mis Match at "+ i +" : Row, so deleting the downloaded File and Repeating the same Process");
				
				for(File file: downloadDirectory.listFiles()) 
				    if (!file.isDirectory()) 
				        file.delete();
				Thread.sleep(10000);
				xlresultStatus = "MisMatched";
				break;				
				//repeatUpload();
				
			}
			else if((xlreader.getCellData("Report", "Validation", i).equalsIgnoreCase("MATCH")))
			{
				System.out.println("There is no Mis Match at All the Row");
				xlresultStatus = "Matched";
			}
			
			System.out.println("The Exact Values from Validation Coulumn are : "+ xlreader.getCellData("Report", "Validation", i));
			
		}
		
			
		
	}
	
	
	
	
	
	public String getTheTaskId(String locator)
	{
		taskid = driver.findElement(By.xpath((properties.getProperty(locator)))).getText().toString();
		System.out.println("Gathered Id from Method is : "+taskid);
		return taskid;
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
			/*
			 * inputvalue ("task_name_textbox", "taskname"); Thread.sleep (5000); inputvalue
			 * (("project_selectbox"), ("project")); Thread.sleep (5000); inputvalue
			 * ("automation_selectbox", "automation"); Thread.sleep (5000); inputvalue
			 * ("targetenvironment_selectbox", "targetenvironment"); Thread.sleep (5000);
			 */
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	public void upload_Medical_Sheet()
	{
		try 
		{
			inputvalue		("task_name_textbox", "taskname");
			Thread.sleep	(5000);
			inputvalue		(("project_selectbox"), ("project"));
			Thread.sleep	(5000);
			inputvalue		("automation_selectbox", "automation_medical");
			Thread.sleep	(5000);
			inputvalue		("targetenvironment_selectbox", "targetenvironment");
			Thread.sleep	(5000);			
			inputvalue		("//input[@id='FileUpload']","medicalUpload_Sheet");
			click			("create_button");
			waitexplicitly	("task_id");
			medicaltaskId = getTheTaskId("task_id");
			System.out.println("Gathered Medical TaskID  : "+taskid);
			Thread.sleep	(5000);
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	public void upload_Dental_Sheet()
	{
		try
		{
			click			("task_topnavigation");	
			waitexplicitly	("createNew_Link");
			click			("createNew_Link");	
			waitexplicitly	("task_name_textbox");
			inputvalue		("task_name_textbox", "taskname");
			Thread.sleep	(5000);
			inputvalue		(("project_selectbox"), ("project"));
			Thread.sleep	(5000);
			inputvalue		("automation_selectbox", "automation_dental");
			Thread.sleep	(5000);
			inputvalue		("targetenvironment_selectbox", "targetenvironment");
			Thread.sleep	(5000);
			inputvalue		("//input[@id='FileUpload']","dental_Sheet");
			Thread.sleep(5000);
			click			("create_button");
			waitexplicitly	("task_id");
			DentalTaskId = getTheTaskId("task_id");
			System.out.println("Gathered Medical TaskID  : "+taskid);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void upload_Another_Sheet() // Need to change the Name Accordingly
	{
		try
		{
			click			("task_topnavigation");	
			waitexplicitly	("createNew_Link");
			click			("createNew_Link");	
			waitexplicitly	("task_name_textbox");
			inputvalue		("task_name_textbox", "taskname");
			Thread.sleep	(5000);
			inputvalue		(("project_selectbox"), ("project"));
			Thread.sleep	(5000);
			inputvalue		("automation_selectbox", "automation_another");
			Thread.sleep	(5000);
			inputvalue		("targetenvironment_selectbox", "targetenvironment");
			Thread.sleep	(5000);
			inputvalue		("//input[@id='FileUpload']","anotherUpload_Sheet"); // I need to change this Value
			Thread.sleep(5000);
			click			("create_button");
			waitexplicitly	("task_id");
			AnotherTaskId = getTheTaskId("task_id");
			System.out.println("Another TaskID  : "+taskid);
			Thread.sleep(5000);			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void check_the_status_Headless()
	{
		try 
		{
			openUrl				(("application_Url"));
			waitexplicitly		("username_textbox");
			inputvalue			(("username_textbox"), ("username"));
			inputvalue			(("password_textbox"), ("password"));
			click				("submit_button");
			waitexplicitly		("task_topnavigation");		
			click				("task_topnavigation");
			
			// Geting the Dental Task Status to confirm it is processing successfully and making sure File should not be correpted
			
			input				(("taskid_searchBar"), (DentalTaskId));
			click				("filter_button");
			waitexplicitly		("task_status");	
			dentalTaskStatus	= verifyStatus	("task_status");
			clear				("taskid_searchBar");
			
			// Geting the Another Task Status to confirm it is processing successfully and making sure File should not be correpted
			
			input				(("taskid_searchBar"), (AnotherTaskId));
			click				("filter_button");
			waitexplicitly		("task_status");
			anotherTaskStatus	= verifyStatus	("task_status");
			clear				("taskid_searchBar");
			
			// Geting the Medical Task Status to confirm it is processing successfully and making sure File should not be correpted
			
			input				(("taskid_searchBar"), (medicaltaskId));
			click				("filter_button");
			waitexplicitly		("task_status");
			medicalTaskStatus	= verifyStatus	("task_status");
			clear				("taskid_searchBar");
			
			
			Thread.sleep(5000); // 			
			
			/*
			 * if(actualValue.equalsIgnoreCase("Completed")) {
			 * System.out.println("In side Completed Loop");
			 * 
			 * click ("task_id_from_Table"); // Click on the Task Id to go inside
			 * waitexplicitly ("download_resultFile"); clickDownLoadsLink
			 * ("download_resultFile"); // Click on the Downloads Link Thread.sleep(5000);
			 * click("logout_button"); //closeBrowser(); rename_ResultFile();
			 * xl_Validation(); status = "Pass"; closeBrowser(); //return status; } else
			 * if(actualValue.equalsIgnoreCase("Processing")) {
			 * System.out.println("In side Processing Loop"); Thread.sleep(5000); status =
			 * "Wait"; click("logout_button"); closeBrowser();
			 * 
			 * }
			 */
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			//closeBrowser();
		}
	
	}
	
	public void do_the_Download()
	{
		try 
		{
			// Dental
			if(dentalTaskStatus.equalsIgnoreCase("Completed"))
			{
				
				openUrl				(("application_Url"));
				waitexplicitly		("username_textbox");
				inputvalue			(("username_textbox"), ("username"));
				inputvalue			(("password_textbox"), ("password"));
				click				("submit_button");
				waitexplicitly		("task_topnavigation");		
				click				("task_topnavigation");
				input				(("taskid_searchBar"), (DentalTaskId));
				click				("filter_button");
				waitexplicitly		("task_status");	
				
				click ("task_id_from_Table"); 
				waitexplicitly ("download_resultFile"); 
				click("download_resultFile"); 
				Thread.sleep(5000);
				click("logout_button"); 
				closeBrowser(); 
				rename_ResultFile();
				xl_Validation_headless();
			}
			else if(dentalTaskStatus.equalsIgnoreCase("Processing"))
			{
				
			}
			
			if(dentalTaskStatus.equalsIgnoreCase("Correpted") || (xlresultStatus == "MisMatched"))
			{
				
			}
			
			//Another
			
			if(anotherTaskStatus.equalsIgnoreCase("Completed"))
			{
				
			}
			else if(anotherTaskStatus.equalsIgnoreCase("Processing"))
			{
				
			}
			
			if(anotherTaskStatus.equalsIgnoreCase("Correpted") || (xlresultStatus == "MisMatched"))
			{
				
			}
			
			// Medical 
			if(medicalTaskStatus.equalsIgnoreCase("Completed"))
			{
				
			}
			else if(medicalTaskStatus.equalsIgnoreCase("Processing"))
			{
				
			}
			
			if(medicalTaskStatus.equalsIgnoreCase("Correpted") || (xlresultStatus == "MisMatched"))
			{
				
			}
			
			
			
			
			
			
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
		
}
