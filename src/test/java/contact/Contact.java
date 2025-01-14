package com.contact;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Contact {

	 WebDriver driver;
	
		@BeforeMethod
		 public void setUp() throws InterruptedException {
		    WebDriverManager.chromedriver().setup();
		    driver = new ChromeDriver();
	        driver.get("https://stage.schedulehub.io/");
	        driver.manage().window().maximize();
		
	        // Login to the application
	        driver.findElement(By.name("email")).sendKeys("test@gmail.com");
	        driver.findElement(By.name("password")).sendKeys("123456");
	        driver.findElement(By.className("_submitBtn_1e7ib_96")).click();
	        Thread.sleep(4000);
	        System.out.println("Login Successfully");
	        Thread.sleep(5000);
		
	        WebElement button = driver.findElement(By.xpath("//button[text()='Add Contact']"));
            button.click();       	
	}
		
		 @Test(priority = 1)
		  public void emptyFields() throws InterruptedException 
		    {
			  driver.findElement(By.name("firstName")).sendKeys("");
			  driver.findElement(By.name("lastName")).sendKeys("");
			  driver.findElement(By.name("phoneNo")).sendKeys("");
			  driver.findElement(By.cssSelector("div.css-1xo9pa4"));
			  driver.findElement(By.name("email"));
			  driver.findElement(By.cssSelector("[aria-labelledby='leadType leadType']"));
			  Thread.sleep(2000);
			  driver.findElement(By.xpath("//button[contains(text(),'Add Client')]")).click();
			  Thread.sleep(1000);
			  
			  WebElement toastElement = driver.findElement(By.xpath("//p[text()='This is required field']"));
			  
				// Get the toaster message text
			    String actualMessage = toastElement.getText();
			    System.out.println("Actual Toast Message: " + actualMessage);

			    // Define the expected message
			    String expectedMessage = "This is required field";

			    // Verify the toaster message
			    Assert.assertEquals(actualMessage.trim(), expectedMessage, "The toast message match the expected value."); 	  
		  }

		  @Test(priority = 2) // Check with repeated email
		  public void repeatedEmail() throws InterruptedException {
			  driver.findElement(By.name("firstName")).sendKeys("Sneha");
			  driver.findElement(By.name("lastName")).sendKeys("Gautam");
			  driver.findElement(By.name("phoneNo")).sendKeys("9876543245");
			  driver.findElement(By.cssSelector("div.css-1xo9pa4")).click();
			  driver.findElement(By.xpath("//li[@data-value='cell']")).click();
			  driver.findElement(By.name("email")).sendKeys("lead@yopmail.com");
			  driver.findElement(By.cssSelector("[aria-labelledby='leadType leadType']")).click();
			  Thread.sleep(2000);
			  driver.findElement(By.xpath("//li[contains(text(),'test')]")).click();
			  driver.findElement(By.cssSelector("[aria-labelledby='medium medium']")).click();
			  Thread.sleep(2000);
			  driver.findElement(By.xpath("//li[contains(text(),'Instagram')]")).click();
			  driver.findElement(By.xpath("//button[contains(text(),'Add Client')]")).click();
			  Thread.sleep(1000);
			  
			  WebElement toastElement = driver.findElement(By.xpath("//div[text()='Email already exists']"));
			  
				// Get the toaster message text
			    String actualMessage = toastElement.getText();
			    System.out.println("Actual Toast Message: " + actualMessage);

			    // Define the expected message
			    String expectedMessage = "Email already exists";

			    // Verify the toaster message
			    Assert.assertEquals(actualMessage.trim(), expectedMessage, "The toast message match the expected value.");
		  }
		  
		 
		  
		  @Test(priority = 3) //Check with invalid Email address
		  public void invalidEmail() throws InterruptedException {
			  driver.findElement(By.name("firstName")).sendKeys("Sneha");
			  driver.findElement(By.name("lastName")).sendKeys("Gautam");
			  driver.findElement(By.name("phoneNo")).sendKeys("9876543245");
			  driver.findElement(By.cssSelector("div.css-1xo9pa4")).click();
			  driver.findElement(By.xpath("//li[@data-value='cell']")).click();
			  driver.findElement(By.name("email")).sendKeys("leadyopmail.com");
			  driver.findElement(By.cssSelector("[aria-labelledby='leadType leadType']")).click();
			  Thread.sleep(2000);
			  driver.findElement(By.xpath("//li[contains(text(),'test')]")).click();
			  driver.findElement(By.cssSelector("[aria-labelledby='medium medium']")).click();
			  Thread.sleep(2000);
			  driver.findElement(By.xpath("//li[contains(text(),'Instagram')]")).click();
			  driver.findElement(By.xpath("//button[contains(text(),'Add Client')]")).click();
			  Thread.sleep(1000);
			  
			  WebElement toastElement = driver.findElement(By.xpath("//p[text()='Please enter a valid email address']"));
			  
				// Get the toaster message text
			    String actualMessage = toastElement.getText();
			    System.out.println("Actual Toast Message: " + actualMessage);

			    // Define the expected message
			    String expectedMessage = "Please enter a valid email address";

			    // Verify the toaster message
			    Assert.assertEquals(actualMessage.trim(), expectedMessage, "The toast message match the expected value.");
		  }
		  
		  @Test(priority = 4) //Check with Valid Client's credentials
        public void contact() throws InterruptedException {
			  driver.findElement(By.name("firstName")).sendKeys("Sneha");
			  driver.findElement(By.name("lastName")).sendKeys("Gautam");
			  driver.findElement(By.name("phoneNo")).sendKeys("9876543245");
			  driver.findElement(By.cssSelector("div.css-1xo9pa4")).click();
			  driver.findElement(By.xpath("//li[@data-value='cell']")).click();
			  driver.findElement(By.name("email")).sendKeys("a@yopmail.com");
			  driver.findElement(By.cssSelector("[aria-labelledby='leadType leadType']")).click();
			  Thread.sleep(2000);
			  driver.findElement(By.xpath("//li[contains(text(),'test')]")).click();
			  driver.findElement(By.cssSelector("[aria-labelledby='medium medium']")).click();
			  Thread.sleep(2000);
			  driver.findElement(By.xpath("//li[contains(text(),'Instagram')]")).click();
            driver.findElement(By.xpath("//button[contains(text(),'Add Client')]")).click();
		      Thread.sleep(1000); 
			 
		  WebElement toastElement = driver.findElement(By.xpath("//div[text()='Lead created successfully']"));
		  
			// Get the toaster message text
		    String actualMessage = toastElement.getText();
		    System.out.println("Actual Toast Message: " + actualMessage);

		    // Define the expected message
		    String expectedMessage = "Lead created successfully";

		    // Verify the toaster message
		    Assert.assertEquals(actualMessage.trim(), expectedMessage, "The toast message match the expected value.");
	  }			   
}
