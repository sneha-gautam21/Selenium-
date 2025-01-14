package billing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

public class FetchRefundedDetails {
    public static void main(String[] args) throws InterruptedException {
    	 // Setup and initialize the ChromeDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        // Navigate to the site and maximize the window
        driver.get("https://dev.schedulehub.io/");
            driver.manage().window().maximize();
            
            // Login to the application
	        driver.findElement(By.name("email")).sendKeys("test@gmail.com");
	        driver.findElement(By.name("password")).sendKeys("123456");
	        driver.findElement(By.className("_submitBtn_yye06_97")).click();

	        // Wait for the login to complete
	        Thread.sleep(4000);
	        System.out.println("Login Successfully");

	        // Navigate to Reports -> Billing Report
	        Thread.sleep(3000);
	        WebElement reportsElement = driver.findElement(By.xpath("//div[contains(text(), 'Reports')]"));
	        reportsElement.click();
	        
	        WebElement billingReportButton = driver.findElement(By.xpath("//button[text()='Billing Report']"));
	        billingReportButton.click();

	        // Wait for the Billing Report table to load
	        Thread.sleep(3000);

            // Locate all rows with "Refunded" status
            List<WebElement> refundedRows = driver.findElements(By.xpath("//div[contains(@style, 'background: rgb(226, 166, 11)') and text()='Refunded']/ancestor::tr"));

            // Iterate through each refunded row and extract details
            for (WebElement row : refundedRows) {
                String parent = row.findElement(By.xpath("./td[1]")).getText();
                String invoiceId = row.findElement(By.xpath("./td[4]")).getText();
                String totalInvoice = row.findElement(By.xpath("./td[6]")).getText();
                String totalPaid = row.findElement(By.xpath("./td[11]")).getText();
                String status = row.findElement(By.xpath("./td[12]")).getText();
                
                // Print the details
                System.out.println("Refunded Row Details:");
                System.out.println("Parent: " + parent);         
                System.out.println("Invoice ID: " + invoiceId);            
                System.out.println("Total Invoice: " + totalInvoice);
                System.out.println("Total Paid: " + totalPaid);
                System.out.println("Status: " + status);
                System.out.println("-------------------------");
            }
            
            // Find all rows in the table
            List<WebElement> failedRows = driver.findElements(By.xpath("//tr[.//div[contains(text(), 'Failed')]]"));
            
            for (WebElement rows : failedRows) {
               
                    // Extract the relevant data from the row
                    String parent = rows.findElement(By.xpath(".//td[1]")).getText();         
                    String invoiceId = rows.findElement(By.xpath(".//td[3]")).getText();
                    String totalInvoice = rows.findElement(By.xpath(".//td[6]")).getText();
                    String surcharge = rows.findElement(By.xpath(".//td[7]")).getText();
                    String discounts = rows.findElement(By.xpath(".//td[8]")).getText();
                    String credits = rows.findElement(By.xpath(".//td[9]")).getText();
                    String totalPaid = rows.findElement(By.xpath(".//td[11]")).getText();
                    String status = rows.findElement(By.xpath("./td[12]")).getText();

                    // Output the extracted data
                    System.out.println("Parent: " + parent);                 
                    System.out.println("Invoice ID: " + invoiceId);
                    System.out.println("Total Invoice: " + totalInvoice);
                    System.out.println("Surcharge: " + surcharge);
                    System.out.println("Discounts: " + discounts);
                    System.out.println("Credits: " + credits);
                    System.out.println("Total Paid: " + totalPaid);
                    System.out.println("Status: " + status);
                    System.out.println("----------------------------");
                }
            
            // Find all rows in the table
            List<WebElement> scheduledRows = driver.findElements(By.xpath("//tr[.//div[contains(text(), 'Scheduled')]]"));
            
            for (WebElement rows : scheduledRows) {
               
                    // Extract the relevant data from the row
                    String parent = rows.findElement(By.xpath(".//td[1]")).getText();         
                    String invoiceId = rows.findElement(By.xpath(".//td[3]")).getText();
                    String totalInvoice = rows.findElement(By.xpath(".//td[6]")).getText();
                    String surcharge = rows.findElement(By.xpath(".//td[7]")).getText();
                    String discounts = rows.findElement(By.xpath(".//td[8]")).getText();
                    String credits = rows.findElement(By.xpath(".//td[9]")).getText();
                    String totalPaid = rows.findElement(By.xpath(".//td[11]")).getText();
                    String status = rows.findElement(By.xpath("./td[12]")).getText();

                    // Output the extracted data
                    System.out.println("Parent: " + parent);                 
                    System.out.println("Invoice ID: " + invoiceId);
                    System.out.println("Total Invoice: " + totalInvoice);
                    System.out.println("Surcharge: " + surcharge);
                    System.out.println("Discounts: " + discounts);
                    System.out.println("Credits: " + credits);
                    System.out.println("Total Paid: " + totalPaid);
                    System.out.println("Status: " + status);
                    System.out.println("----------------------------");
                }
            
            driver.close();
            
            }
}  
 
    
