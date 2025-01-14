package com.tdd.Data_Driven_Test;

import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.utility.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Data_ProviderTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://stage.schedulehub.io/");
		driver.manage().window().maximize();
	}

	@DataProvider
	public Iterator<Object[]> getTestData() {
		ArrayList<Object[]> testData = TestUtil.getDataFromExcel();
		return testData.iterator();

	}

	@Test(dataProvider = "getTestData")
	public void twelve_Tone(String email, String password) throws InterruptedException
	{
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(email);
		System.out.println("Email : "+email);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);
		System.out.println("Password : "+password);
       
		Thread.sleep(3000);

		driver.findElement(By.className("_submitBtn_yye06_97")).click();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
