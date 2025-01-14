package billing;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;


public class Total_Invoice {

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

	        // Variable to store the sum of all Total Invoice amounts
	        double totalInvoiceSum = 0;

	        // Iterate through each row and accumulate Total Invoice values
	        for (int i = 1; i <= rows.size(); i++) {
	            // Fetch the text in the "Total Invoice" cell of the current row
	            String totalInvoiceText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[6]")).getText();

	            // Check if the totalInvoiceText is empty or only contains whitespace
	            if (totalInvoiceText != null && !totalInvoiceText.trim().isEmpty()) {
	                // Remove the dollar sign and convert to double
	                double totalInvoice = Double.parseDouble(totalInvoiceText.replace("$", "").trim());

	                // Add to the total sum
	                totalInvoiceSum += totalInvoice;

	                System.out.println("Total Invoice for row " + i + ": $" + totalInvoice);
	            } else {
	                System.out.println("Total Invoice for row " + i + " is empty or missing.");
	            }
	        }

	        System.out.println("Sum of all Total Invoices: $" + totalInvoiceSum);

	        // Close the WebDriver
	        driver.quit();
	    }    
	}

