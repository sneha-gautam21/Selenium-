package com.tdd.Data_Driven_Test;

import java.io.ObjectInputFilter.Status;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.excel.utility.Xls_Reader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ParameterizeTest {

	public static void main(String[] args) throws InterruptedException {

		//webDriver code
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();		
		driver.get("https://stage.schedulehub.io/"); // Launch url

		//get test data from excel
		Xls_Reader reader = new Xls_Reader("C:\\Users\\Monika appricott\\Documents\\Selenium Workspace\\Data_Driven_Test\\src\\main\\java\\com\\testdata\\ToneLogin.xlsx");

		reader.addColumn("Sheet1","Status"); //adding column in excel sheet

		int rowCount = reader.getRowCount("Sheet1");
		for(int rowNum = 2; rowNum <= rowCount; rowNum++) {

			String email = reader.getCellData("Sheet1","Email", rowNum);

			//String password = reader.getCellData("Sheet1","Password",2);
			String password = reader.getCellData("Sheet1", "Password", rowNum);
			if (password.endsWith(".0")) {
			    password = password.substring(0, password.length() - 2);
			}


			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys(email); 
			System.out.println("Email: " + email);
			
         // By WebElement
//			 WebElement emailField = driver.findElement(By.name("email"));
//			   emailField.clear();
//             emailField.sendKeys(email);
//             System.out.println("Email: " + email);

			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys(password);
			System.out.println("Password: " + password);

			Thread.sleep(3000);
			driver.findElement(By.className("_submitBtn_yye06_97")).click();
			
			reader.setCellData("Sheet1","Status", rowNum,"Pass");   //write data into cell of excel sheet

		}
	}
}


