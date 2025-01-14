package test.seleniumProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FirstSeleniumProject {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://stage.schedulehub.io/");
		
		// Maximize the browser
	    driver.manage().window().maximize();
	    
		driver.findElement(By.name("email")).sendKeys("test@gmail.com");
		driver.findElement(By.name("password")).sendKeys("123456");
		driver.findElement(By.className("_submitBtn_yye06_97")).click();
		
			Thread.sleep(4000);
		
		driver.close();
		
		System.out.println("Test Completed Successfully");
		
	}
}
