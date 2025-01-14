package billing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Tenth_Row {

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
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody")));

        // Use JavascriptExecutor to scroll to the bottom of the page (or the container)
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Loop to scroll the page/container and check if 10th row is visible
        boolean rowVisible = false;
        while (!rowVisible) {
            try {
                // Try to locate the 10th row's "Total Invoice" column and check if it is visible
                WebElement tenthRow = driver.findElement(By.xpath("//table/tbody/tr[10]/td[6]"));
                if (tenthRow.isDisplayed()) {
                    rowVisible = true;  // Exit the loop if row is visible
                }
            } catch (Exception e) {
                // If the 10th row isn't visible, scroll down and check again
                js.executeScript("window.scrollBy(0, 500);");  // Scroll down by 500 pixels
                Thread.sleep(2000);  // Wait for content to load
            }
        }

        // Wait for the 10th row's columns to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody/tr[10]/td[6]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody/tr[10]/td[8]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody/tr[10]/td[9]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody/tr[10]/td[11]")));

        // Get the data from the 10th row
        String totalInvoiceText = driver.findElement(By.xpath("//table/tbody/tr[10]/td[6]")).getText();
        String discountText = driver.findElement(By.xpath("//table/tbody/tr[10]/td[8]")).getText();
        String creditsText = driver.findElement(By.xpath("//table/tbody/tr[10]/td[9]")).getText();
        String totalPaidText = driver.findElement(By.xpath("//table/tbody/tr[10]/td[11]")).getText();

        // Output the data for the 10th row
        System.out.println("Data for the 10th Row:");
        System.out.println("Total Invoice: " + totalInvoiceText);
        System.out.println("Discount: " + discountText);
        System.out.println("Credits: " + creditsText);
        System.out.println("Total Paid: " + totalPaidText);

        // Close the WebDriver
        driver.quit();
    }
}
