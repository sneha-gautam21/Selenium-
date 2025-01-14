package billing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Fetch_Invoice {

    public static void main(String[] args) throws InterruptedException {

        // Setup and initialize the ChromeDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        // Navigate to the site and maximize the window
        driver.get("https://stage.schedulehub.io/");
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

        // Locate all rows in the table
        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        
        // Iterate through each row and print the details
        for (int i = 1; i <= rows.size(); i++) {
            // Fetch and print each cell's text in the current row
            String parent = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[1]//h6")).getText();
            String program = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[2]//h6")).getText();
            String invoiceId = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[4]")).getText();
            String billingDate = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[5]")).getText();
            String totalInvoice = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[6]")).getText();
            String totalDue = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[10]")).getText();
            String status = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[12]//div")).getText();

            System.out.println("Details of row " + i + ":");
            System.out.println("Parent: " + parent);
            System.out.println("Program: " + program);
            System.out.println("Invoice Id: " + invoiceId);
            System.out.println("Billing Date: " + billingDate);
            System.out.println("Total Invoice: " + totalInvoice);
            System.out.println("Total Due: " + totalDue);
            System.out.println("Status: " + status);
            System.out.println("----------------------");
        }

       
    }    
}
