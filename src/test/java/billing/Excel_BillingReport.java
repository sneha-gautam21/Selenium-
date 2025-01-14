package billing;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Excel_BillingReport {

    public static void main(String[] args) throws InterruptedException, IOException {

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
        double totalSurchargeSum = 0;

        // Variables to store the sums for failed and scheduled rows
        double failedInvoiceSum = 0;
        double failedDiscountsSum = 0;
        double failedCreditsSum = 0;
        double failedSurchargeSum = 0;
        double failedTotalPaidSum = 0;

        double scheduledInvoiceSum = 0;
        double scheduledDiscountsSum = 0;
        double scheduledCreditsSum = 0;
        double scheduledSurchargeSum = 0;
        double scheduledTotalPaidSum = 0;
        
        double refundedInvoiceSum =0;
        double refundedDiscountsSum = 0;
        double refundedCreditsSum = 0;
        double refundedSurchargeSum = 0;
        double refundedTotalPaidSum = 0;


        // Iterate through each row and accumulate values for Total Invoice, Discounts, Credits, and Total Paid
        for (int i = 1; i <= rows.size(); i++) {
          
            String totalInvoiceText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[6]")).getText();
            String surchargeText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[7]")).getText();
            String discountText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[8]")).getText();
            String creditsText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[9]")).getText();
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

        // Locate all rows with "Refunded" status
        List<WebElement> refundedRows = driver.findElements(By.xpath("//div[contains(@style, 'background: rgb(226, 166, 11)') and text()='Refunded']/ancestor::tr"));

        // Iterate through each refunded row and extract details
        for (WebElement row : refundedRows) {
            String totalInvoiceText = row.findElement(By.xpath("./td[6]")).getText();
            String surchargeText = row.findElement(By.xpath("./td[7]")).getText();
            String discountText = row.findElement(By.xpath("./td[8]")).getText();
            String totalPaid = row.findElement(By.xpath("./td[11]")).getText();
            String creditsText = row.findElement(By.xpath("./td[9]")).getText();

            refundedTotalPaidSum += parseAmount(totalPaid, "Refunded Total Paid", -1);
            refundedSurchargeSum += parseAmount(surchargeText, "Refunded Total Paid", -1);
            refundedDiscountsSum += parseAmount(discountText, "Refunded Total Paid", -1);
            refundedCreditsSum += parseAmount(creditsText, "Refunded Total Paid", -1);
            refundedInvoiceSum += parseAmount(totalInvoiceText, "Refunded Total Paid", -1);

        }

        // Find all rows in the table for failed and scheduled rows
        List<WebElement> failedRows = driver.findElements(By.xpath("//tr[.//div[contains(text(), 'Failed')]]"));
        for (WebElement row : failedRows) {
        	String failedInvoiceText = row.findElement(By.xpath("./td[6]")).getText();
            String surcharge = row.findElement(By.xpath(".//td[7]")).getText();
            String discounts = row.findElement(By.xpath(".//td[8]")).getText();
            String credits = row.findElement(By.xpath(".//td[9]")).getText();
            String totalPaid = row.findElement(By.xpath(".//td[11]")).getText();

            failedDiscountsSum += parseAmount(discounts, "Failed Discount", -1);
            failedCreditsSum += parseAmount(credits, "Failed Credits", -1);
            failedSurchargeSum += parseAmount(surcharge, "Failed Surcharge", -1);
            failedTotalPaidSum += parseAmount(totalPaid, "Failed Total Paid", -1);
            failedInvoiceSum += parseAmount(failedInvoiceText, "Refunded Total invoice", -1);
        }

        List<WebElement> scheduledRows = driver.findElements(By.xpath("//tr[.//div[contains(text(), 'Scheduled')]]"));
        for (WebElement row : scheduledRows) {
        	String scheduledInvoiceText = row.findElement(By.xpath("./td[6]")).getText();
            String surcharge = row.findElement(By.xpath(".//td[7]")).getText();
            String discounts = row.findElement(By.xpath(".//td[8]")).getText();
            String credits = row.findElement(By.xpath(".//td[9]")).getText();
            String totalPaid = row.findElement(By.xpath(".//td[11]")).getText();

            scheduledDiscountsSum += parseAmount(discounts, "Scheduled Discount", -1);
            scheduledCreditsSum += parseAmount(credits, "Scheduled Credits", -1);
            scheduledSurchargeSum += parseAmount(surcharge, "Scheduled Surcharge", -1);
            scheduledTotalPaidSum += parseAmount(totalPaid, "Scheduled Total Paid", -1);
            refundedInvoiceSum += parseAmount(scheduledInvoiceText, "Refunded Total invoice", -1);
        }
 
        // Calculate Amount and Expected Amount
        double expected = totalInvoiceSum - (totalDiscountsSum + totalCreditsSum);
        double totalExpected = expected + refundedTotalPaidSum;


        // Create an Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Billing Report");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] columns = { "Category", "Sum of Total Invoices", "Sum of Total Surcharge", "Sum of Discounts", 
                              "Sum of Credits", "Sum of Total Paid" };

        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        // Add the data rows for totals
        int rowIndex = 1;
        Row row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("Total");
        row.createCell(1).setCellValue(totalInvoiceSum);
        row.createCell(2).setCellValue(totalSurchargeSum);
        row.createCell(3).setCellValue(totalDiscountsSum);
        row.createCell(4).setCellValue(totalCreditsSum);
        row.createCell(5).setCellValue(totalPaidSum);

        // Add Failed Rows Data
        row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("Failed Rows");
        row.createCell(1).setCellValue(failedInvoiceSum);
        row.createCell(2).setCellValue(failedSurchargeSum);
        row.createCell(3).setCellValue(failedDiscountsSum);
        row.createCell(4).setCellValue(failedCreditsSum);
        row.createCell(5).setCellValue(failedTotalPaidSum);

        // Add Scheduled Rows Data
        row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("Scheduled Rows");
        row.createCell(1).setCellValue(scheduledInvoiceSum);
        row.createCell(2).setCellValue(scheduledSurchargeSum);
        row.createCell(3).setCellValue(scheduledDiscountsSum);
        row.createCell(4).setCellValue(scheduledCreditsSum);
        row.createCell(5).setCellValue(scheduledTotalPaidSum);
        
     // Add Refunded Rows Data
        row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("Refunded Rows");
        row.createCell(1).setCellValue(scheduledInvoiceSum);
        row.createCell(2).setCellValue(refundedSurchargeSum);
        row.createCell(3).setCellValue(refundedDiscountsSum);
        row.createCell(4).setCellValue(refundedCreditsSum);
        row.createCell(5).setCellValue(refundedTotalPaidSum);

        // Add Expected Amount and Amount
        row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("Expected Amount");
        row.createCell(1).setCellValue(totalExpected);  // Amount 
        
        double realSurcharge = totalSurchargeSum - (failedSurchargeSum + refundedSurchargeSum + scheduledSurchargeSum);

     // Add Remaining Surcharge to the Excel file
        row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("Real Surcharge");
        row.createCell(1).setCellValue(realSurcharge);

        double realDicount = totalDiscountsSum - (failedDiscountsSum + scheduledDiscountsSum + refundedDiscountsSum);
        
        row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("Real Discount");
        row.createCell(1).setCellValue(realDicount);
        
        double realCredits = totalCreditsSum - (failedCreditsSum + scheduledCreditsSum + refundedCreditsSum);
        
        row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("Real Credits");
        row.createCell(1).setCellValue(realCredits);
        
        double realTotalPaid = totalPaidSum - (failedTotalPaidSum + scheduledTotalPaidSum - refundedTotalPaidSum);
        
        row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("Real Total paid");
        row.createCell(1).setCellValue(realTotalPaid);
        
        // Write the Excel file
        try (FileOutputStream fileOut = new FileOutputStream("Billing12Report.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the WebDriver
        driver.quit();
        System.out.println("Excel file created");
    }

    private static double parseAmount(String amountText, String label, int rowIndex) {
        if (amountText != null && !amountText.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountText.replace("$", "").trim());
                return amount;
            } catch (NumberFormatException e) {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
