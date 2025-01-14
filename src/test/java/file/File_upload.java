
package com.file;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class File_upload {
	
    public static void main(String[] args) {
      
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://tus.io/demo");
        driver.manage().window().maximize();

        WebElement uploadElement = driver.findElement(By.id("P0-0"));

        String filePath = "C:/Users/Monika appricott/Downloads/logo"; 
        uploadElement.sendKeys(filePath);

        System.out.println("File uploaded successfully!");

    }
}

