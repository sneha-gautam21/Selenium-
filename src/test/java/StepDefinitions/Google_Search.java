package StepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Google_Search {
	
	WebDriver driver;
	
//	@Given("browser is open")
//	public void browser_is_open() {
//	    System.out.println("Inside Step - browser is open");
//	    
//	    WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//	}
//
//	@And("user is on google search page")
//	public void user_is_on_google_search_page() {
//	    System.out.println("Inside Step - user is on google search page");
//	    
//	    driver.get("https://google.com/");
//        driver.manage().window().maximize();
//
//	}
//
//	@When("user enter a text in search box")
//	public void user_enter_a_text_in_search_box() throws InterruptedException {
//	    System.out.println("Inside Step - user enter a text in search box");
//	    
//	    driver.findElement(By.name("q")).sendKeys("Automation step by step");
//	    
//	    Thread.sleep(2000);
//
//	}
//
//	@And("hits enter")
//	public void hits_enter() {
//	    System.out.println("Inside Step - hits enter");
//
//	    driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
//	}
//
//	@Then("user is navigated to search results")
//	public void user_is_navigated_to_search_results() {
//	    System.out.println("Inside Step - user is navigated to search results");
//
//	    driver.getPageSource().contains("Online Courses");
//	    
//	    driver.close();
//	    driver.quit();
//	}
//
}
