package billing;

	import java.io.File;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.util.List;
	import org.apache.poi.ss.usermodel.*;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import io.github.bonigarcia.wdm.WebDriverManager;

public class Excel_AllCounts {

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

	        // Create a new Excel workbook
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Billing Report");

	        // Create the header row
	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("Total Invoice");
	        headerRow.createCell(1).setCellValue("Total Surcharge");
	        headerRow.createCell(2).setCellValue("Discounts");
	        headerRow.createCell(3).setCellValue("Credits");
	        headerRow.createCell(4).setCellValue("Total Paid");

	        // Iterate through each row and accumulate values for Total Invoice, Discounts, Credits, and Total Paid
	        int rowIndex = 1;
	        for (int i = 1; i <= rows.size(); i++) {
	        	
	            // Fetch the text in the "Total Invoice" cell of the current row (column 6)
	            String totalInvoiceText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[6]")).getText();
	            String surchargeText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[7]")).getText();
	            String discountText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[8]")).getText();
	            String creditsText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[9]")).getText();
	            String totalPaidText = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[11]")).getText();

	            // Add the parsed amounts
	            totalInvoiceSum += parseAmount(totalInvoiceText);
	            totalSurchargeSum += parseAmount(surchargeText);
	            totalDiscountsSum += parseAmount(discountText);
	            totalCreditsSum += parseAmount(creditsText);
	            totalPaidSum += parseAmount(totalPaidText);

	            // Write the data to the Excel sheet
	            Row dataRow = sheet.createRow(rowIndex++);
	            dataRow.createCell(0).setCellValue(parseAmount(totalInvoiceText));
	            dataRow.createCell(1).setCellValue(parseAmount(surchargeText));
	            dataRow.createCell(2).setCellValue(parseAmount(discountText));
	            dataRow.createCell(3).setCellValue(parseAmount(creditsText));
	            dataRow.createCell(4).setCellValue(parseAmount(totalPaidText));
	        }

	        // Output the sum for each column
	        double refundedTotalPaidSum = 0;

	        // Locate all rows with "Refunded" status
	        List<WebElement> refundedRows = driver.findElements(By.xpath("//div[contains(@style, 'background: rgb(226, 166, 11)') and text()='Refunded']/ancestor::tr"));

	        // Iterate through each refunded row and extract details
	        for (WebElement row : refundedRows) {
	            String totalPaid = row.findElement(By.xpath("./td[11]")).getText();
	            refundedTotalPaidSum += parseAmount(totalPaid);
	        }

	        double expected = totalInvoiceSum - (totalDiscountsSum + totalCreditsSum);
	        double totalExpected = expected + refundedTotalPaidSum;

	        // Write summary to Excel
	        Row summaryRow = sheet.createRow(rowIndex++);
	        summaryRow.createCell(0).setCellValue("Sum of all Total Invoices");
	        summaryRow.createCell(1).setCellValue(totalInvoiceSum);

	        summaryRow = sheet.createRow(rowIndex++);
	        summaryRow.createCell(0).setCellValue("Sum of all Total Surcharge");
	        summaryRow.createCell(1).setCellValue(totalSurchargeSum);

	        summaryRow = sheet.createRow(rowIndex++);
	        summaryRow.createCell(0).setCellValue("Sum of all Discounts");
	        summaryRow.createCell(1).setCellValue(totalDiscountsSum);

	        summaryRow = sheet.createRow(rowIndex++);
	        summaryRow.createCell(0).setCellValue("Sum of all Credits");
	        summaryRow.createCell(1).setCellValue(totalCreditsSum);

	        summaryRow = sheet.createRow(rowIndex++);
	        summaryRow.createCell(0).setCellValue("Sum of all Total Paid");
	        summaryRow.createCell(1).setCellValue(totalPaidSum);

	        summaryRow = sheet.createRow(rowIndex++);
	        summaryRow.createCell(0).setCellValue("Expected Amount");
	        summaryRow.createCell(1).setCellValue(expected);

	        summaryRow = sheet.createRow(rowIndex++);
	        summaryRow.createCell(0).setCellValue("Refunded Total Paid Sum");
	        summaryRow.createCell(1).setCellValue(refundedTotalPaidSum);

	        summaryRow = sheet.createRow(rowIndex++);
	        summaryRow.createCell(0).setCellValue("Total Expected Amount");
	        summaryRow.createCell(1).setCellValue(totalExpected);

	        // Write the Excel file to disk
	        FileOutputStream fileOut = new FileOutputStream(new File("Billing_Report.xlsx"));
	        workbook.write(fileOut);
	        fileOut.close();

	        // Close the WebDriver
	        driver.quit();

	        System.out.println("Excel file created successfully!");
	    }

	    /**
	     * Parses a string to a double. If the string is empty or invalid, it returns 0.
	     * @param amountText The string representing the amount (e.g., "$199.0")
	     * @return The parsed amount (0 if invalid or empty)
	     */
	    private static double parseAmount(String amountText) {
	        if (amountText != null && !amountText.trim().isEmpty()) {
	            try {
	                // Remove dollar sign and trim the string, then parse it to double
	                return Double.parseDouble(amountText.replace("$", "").trim());
	            } catch (NumberFormatException e) {
	                return 0; // Return 0 for invalid data
	            }
	        }
	        return 0;
	    }
	}


