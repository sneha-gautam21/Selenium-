package billing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Excel_data {

	    	    public static void main(String[] args) throws IOException {
	    	        // Setup ChromeDriver (or another browser if desired)
	    	        WebDriverManager.chromedriver().setup();
	    	        ChromeOptions options = new ChromeOptions();
	    	        options.addArguments("--headless"); // run in headless mode
	    	        WebDriver driver = new ChromeDriver(options);

	    	        // Open the web page
	    	        driver.get("https://stage.schedulehub.io/"); // Replace with your URL

	    	        // Locate the table
	    	        WebElement table = driver.findElement(By.xpath("//table[contains(., 'Weekly Lesson')]"));

	    	        // Fetch headers
	    	        List<WebElement> headers = table.findElements(By.xpath(".//th"));
	    	        
	    	        // Fetch rows
	    	        List<WebElement> rows = table.findElements(By.xpath(".//tr"));

	    	        // Create an Excel workbook and sheet
	    	        Workbook workbook = new XSSFWorkbook();
	    	        Sheet sheet = workbook.createSheet("Weekly Lesson Data");

	    	        // Create header row in Excel
	    	        Row headerRow = sheet.createRow(0);
	    	        for (int i = 0; i < headers.size(); i++) {
	    	            Cell cell = headerRow.createCell(i);
	    	            cell.setCellValue(headers.get(i).getText());
	    	        }

	    	        // Create data rows in Excel
	    	        for (int rowIndex = 1; rowIndex < rows.size(); rowIndex++) {
	    	            Row excelRow = sheet.createRow(rowIndex);
	    	            List<WebElement> cells = rows.get(rowIndex).findElements(By.xpath(".//td"));
	    	            for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) {
	    	                Cell cell = excelRow.createCell(cellIndex);
	    	                cell.setCellValue(cells.get(cellIndex).getText());
	    	            }
	    	        }

	    	        // Write the data to an Excel file
	    	        try (FileOutputStream fileOut = new FileOutputStream("Weekly_Lesson_Data.xlsx")) {
	    	            workbook.write(fileOut);
	    	        }

	    	        // Close resources
	    	        workbook.close();
	    	        driver.quit();
	    	        System.out.println("Data exported to Excel successfully.");
	    	    }
	    	}
