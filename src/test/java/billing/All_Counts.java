package billing;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class All_Counts {

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

	        // Locate all rows in the table
	        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));

	        // Variables to store the sums for each column
	        double totalInvoiceSum = 0;
	        double totalDiscountsSum = 0;
	        double totalCreditsSum = 0;
	        double totalPaidSum = 0;
	        double totalSurchargeSum =0;
	        
	        // Variables to store the sums for failed and scheduled rows
	        double failedDiscountsSum = 0;
	        double failedCreditsSum = 0;
	        double failedSurchargeSum = 0;
	        double failedTotalPaidSum = 0;

	        double scheduledDiscountsSum = 0;
	        double scheduledCreditsSum = 0;
	        double scheduledSurchargeSum = 0;
	        double scheduledTotalPaidSum = 0;


	        // Iterate through each row and accumulate values for Total Invoice, Discounts, Credits, and Total Paid
	        for (int i = 1; i <= rows.size(); i++) {
	            // Fetch the text in the "Total Invoice" cell of the current row (column 6)
	            String totalInvoiceText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[6]")).getText();
	            
	            String surchargeText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[7]")).getText();
	            
	            // Fetch the text in the "Discounts" cell of the current row (column 8)
	            String discountText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[8]")).getText();
	            
	            // Fetch the text in the "Credits" cell of the current row (column 9)
	            String creditsText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[9]")).getText();
	            
	            // Fetch the text in the "Total Paid" cell of the current row (column 11)
	            String totalPaidText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[11]")).getText();

	            // Handle Total Invoice column (6)
	            totalInvoiceSum += parseAmount(totalInvoiceText, "Total Invoice", i);
	            
	            totalSurchargeSum += parseAmount(surchargeText, "Surcharge", i);

	            // Handle Discounts column (8)
	            totalDiscountsSum += parseAmount(discountText, "Discount", i);

	            // Handle Credits column (9)
	            totalCreditsSum += parseAmount(creditsText, "Credits", i);

	            // Handle Total Paid column (10)
	            totalPaidSum += parseAmount(totalPaidText, "Total Paid", i);
	        }

	        double refundedTotalPaidSum = 0;
	        
	        // Locate all rows with "Refunded" status
            List<WebElement> refundedRows = driver.findElements(By.xpath("//div[contains(@style, 'background: rgb(226, 166, 11)') and text()='Refunded']/ancestor::tr"));

            // Iterate through each refunded row and extract details
            for (WebElement row : refundedRows) {
                String parent = row.findElement(By.xpath("./td[1]")).getText();
                String invoiceId = row.findElement(By.xpath("./td[4]")).getText();
                String totalInvoice = row.findElement(By.xpath("./td[6]")).getText();
                String totalPaid = row.findElement(By.xpath("./td[11]")).getText();
                String status = row.findElement(By.xpath("./td[12]")).getText();
                
                refundedTotalPaidSum += parseAmount(totalPaid, "Refunded Total Paid", -1);
                
                System.out.println("-----------------------------");
                // Print the details
                System.out.println("Refunded Row Details:");
                System.out.println("-----------------------------");
                System.out.println("Parent: " + parent);         
                System.out.println("Invoice ID: " + invoiceId);            
                System.out.println("Total Invoice: " + totalInvoice);
                System.out.println("Total Paid: " + totalPaid);
                System.out.println("Status: " + status);
                System.out.println("-----------------------------");
            }
      
            // Find all rows in the table
            List<WebElement> failedRows = driver.findElements(By.xpath("//tr[.//div[contains(text(), 'Failed')]]"));
            
            for (WebElement rows1 : failedRows)
            { 
                    String surcharge = rows1.findElement(By.xpath(".//td[7]")).getText();
                    String discounts = rows1.findElement(By.xpath(".//td[8]")).getText();
                    String credits = rows1.findElement(By.xpath(".//td[9]")).getText();
                    String totalPaid = rows1.findElement(By.xpath(".//td[11]")).getText();

                 // Add the values to the respective totals for Failed rows
                    failedDiscountsSum += parseAmount(discounts, "Failed Discount", -1);
                    failedCreditsSum += parseAmount(credits, "Failed Credits", -1);
                    failedSurchargeSum += parseAmount(surcharge, "Failed Surcharge", -1);
                    failedTotalPaidSum += parseAmount(totalPaid, "Failed Total Paid", -1);
                }
            
            // Find all rows in the table
            List<WebElement> scheduledRows = driver.findElements(By.xpath("//tr[.//div[contains(text(), 'Scheduled')]]"));
            
            for (WebElement rows1 : scheduledRows) {

                    String surcharge = rows1.findElement(By.xpath(".//td[7]")).getText();
                    String discounts = rows1.findElement(By.xpath(".//td[8]")).getText();
                    String credits = rows1.findElement(By.xpath(".//td[9]")).getText();
                    String totalPaid = rows1.findElement(By.xpath(".//td[11]")).getText();

                    // Add the values to the respective totals for Scheduled rows
                    scheduledDiscountsSum += parseAmount(discounts, "Scheduled Discount", -1);
                    scheduledCreditsSum += parseAmount(credits, "Scheduled Credits", -1);
                    scheduledSurchargeSum += parseAmount(surcharge, "Scheduled Surcharge", -1);
                    scheduledTotalPaidSum += parseAmount(totalPaid, "Scheduled Total Paid", -1);
                    
                }

	        // Output the sum for each column
	        System.out.println();
	        System.out.println("Sum of all Total Invoices: $" + totalInvoiceSum);
	        System.out.println("Sum of all Total Surcharge: $" + totalSurchargeSum);
	        System.out.println("Sum of all Discounts: $" + totalDiscountsSum);
	        System.out.println("Sum of all Credits: $" + totalCreditsSum);
	        System.out.println("Sum of all Total Paid: $" + totalPaidSum);
	        System.out.println("------------------------------------");
	        
	        System.out.println("Failed Rows:--");
	        System.out.println("Sum of Discounts for Failed Rows: $" + failedDiscountsSum);
	        System.out.println("Sum of Credits for Failed Rows: $" + failedCreditsSum);
	        System.out.println("Sum of Surcharge for Failed Rows: $" + failedSurchargeSum);
	        System.out.println("Sum of Total Paid for Failed Rows: $" + failedTotalPaidSum);
	        System.out.println("--------------------------------------");
	        
	        System.out.println("Scheduled Rows:--");
	        System.out.println("Sum of Discounts for Scheduled Rows: $" + scheduledDiscountsSum);
	        System.out.println("Sum of Credits for Scheduled Rows: $" + scheduledCreditsSum);
	        System.out.println("Sum of Surcharge for Scheduled Rows: $" + scheduledSurchargeSum);
	        System.out.println("Sum of Total Paid for Scheduled Rows: $" + scheduledTotalPaidSum);
            
            double expected = totalInvoiceSum - (totalDiscountsSum + totalCreditsSum);
            
            System.out.println("---------------------------------------");
            System.out.println("Amount: $" + expected);
            System.out.println("---------------------------------------");
            
            double totalExpected = expected + refundedTotalPaidSum;

            System.out.println("---------------------------------------");
            System.out.println("Expected Amount: $" + totalExpected);
            System.out.println("---------------------------------------");

	        // Close the WebDriver
	        driver.quit();
	    }    

	    private static double parseAmount(String amountText, String label, int rowIndex) {
	        if (amountText != null && !amountText.trim().isEmpty()) {
	            try {
	                // Remove dollar sign and trim the string, then parse it to double
	                double amount = Double.parseDouble(amountText.replace("$", "").trim());
	                return amount;
	            } catch (NumberFormatException e) {
	                // In case of invalid format (e.g., non-numeric text)
	                System.out.println("Invalid " + label + " format for row " + rowIndex + ": " + amountText);
	                return 0; // Return 0 for invalid data
	            }
	        } else {
	            return 0;
	        }
	    }
	}

