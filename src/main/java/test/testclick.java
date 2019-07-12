package test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class testclick 
{
	public WebElement element;
	@Test
	public void intialise() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "E:\\SeleniumWorkspace\\TestSample\\src\\test\\resources\\Exefile_Driver\\chromedriver.exe"/*System.getProperty("user.dir")+properties.getProperty("chromedriverexepath")*/);
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.amazon.in/");
		Thread.sleep(5000);
		Actions action = new Actions(driver);
		element = driver.findElement(By.xpath("//a[contains(.,'Sign in securely')]"));
		action.contextClick(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
		
		Thread.sleep(5000);
		driver.quit();
		//a[contains(.,'Sign in securely')]
		
		
		
		
		
		
	}
	
	
}
