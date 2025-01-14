package StepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Login_DemoSteps {
	
	WebDriver driver;
	
	@Given("browser is open")
	public void browser_is_open() {
		WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
	}

	@And("user is on login page")
	public void user_is_on_login_page() {
		driver.get("https://dev.schedulehub.io/");
        driver.manage().window().maximize();
	}

	@When("^user enters (.*) and (.*)$")
	public void user_enters_username_and_password(String username, String password) throws InterruptedException {
	   
		driver.findElement(By.name("email")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        
        Thread.sleep(2000);
		
	}
	
	@And("user clicks on login")
	public void user_clicks_on_login() throws InterruptedException {
		
		  driver.findElement(By.className("_submitBtn_yye06_97")).click();
		  
		  Thread.sleep(2000);
	}

	@Then("user is navigated to the home page")
	public void user_is_navigated_to_the_home_page() {
	   
		driver.findElement(By.xpath("//img[@alt='logo']"));
		System.out.println("Logo Found Successfully");
		
		driver.quit();
	}
}
