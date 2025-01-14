package com.tdd.Data_Driven_Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.excel.utility.Xls_Reader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DataDrivenTest {

	public static void main(String[] args) {

		//get test data from excel
		Xls_Reader reader = new Xls_Reader("C:\\Users\\Monika appricott\\Documents\\Selenium Workspace\\Data_Driven_Test\\src\\main\\java\\com\\testdata\\ToneLogin.xlsx");


		String email = reader.getCellData("Sheet1","Email",2);  
		System.out.println(email);

		String password = reader.getCellData("Sheet1","Password",2);
		System.out.println(password);

		//webDriver code
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();		
		driver.get("https://stage.schedulehub.io/");   // Launch Url


		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.className("_submitBtn_yye06_97")).click();

	}
}
