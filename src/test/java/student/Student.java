package com.student;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Student {

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
	
	        WebElement lead = driver.findElement(By.xpath("//td//h6[text()='Sneha Gautam']"));
	        lead.click();
	        
	        Thread.sleep(1000);
	        WebElement addStudent = driver.findElement(By.xpath("//div[contains(@class, 'MuiListItemText-root')]//span[text()='Add Student']"));
	        addStudent.click();
 
          }
		
		@Test
		public void emptyFields()
		{
			}
		
		@AfterMethod
		public void tearDown() {
			driver.quit();
		}
}
