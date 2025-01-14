package billing;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Drum_Lesson {

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://stage.schedulehub.io/");
        driver.manage().window().maximize();

        // Login
        driver.findElement(By.name("email")).sendKeys("test@gmail.com");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.className("_submitBtn_yye06_97")).click();

        Thread.sleep(4000);  // Wait for login to complete
        System.out.println("Login Successfully");
        Thread.sleep(3000);

        // Navigate to the "Lesson Schedule" page
        WebElement schedule = driver.findElement(By.xpath("//a[@href='#/schedule/teacherSchedule']"));
        schedule.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement lessonSchedule = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Lesson Schedule']")));
        lessonSchedule.click();

        Thread.sleep(3000);

        // Locate the "DL60" class containers based on text content
        List<WebElement> dl60Class = driver.findElements(By.xpath("//p[contains(text(), 'DL60')]//ancestor::div[contains(@class, '_card_')]"));

        // Initialize total student counter
        int totalStudentsAcrossDl60 = 0;

        // Iterate through each "DL60" class container
        for (WebElement classItem : dl60Class) {
            // Locate all students within the "DL60" class container
            List<WebElement> studentElements = classItem.findElements(By.xpath(".//div[contains(@class, '_hoverGrayBg_mwqdo_463')]/p[contains(@class, '_listItem_mwqdo_286') and contains(text(), 'yrs')]"));

            // Print each student’s details for the current "DL60" class
            System.out.println("Students in this DL60 class:");
            for (WebElement student : studentElements) {
                System.out.println("Student: " + student.getText());
            }

            // Print the number of students in the current "DL60" class
            System.out.println("Total students in this DL60 class: " + studentElements.size());

            // Add the number of students in this "DL60" class to the total counter
            totalStudentsAcrossDl60 += studentElements.size();
        }

        // Print the total count of students across all "DL60" classes
        System.out.println("\nTotal students across all DL60 classes: " + totalStudentsAcrossDl60);

        // Print the total count of DL60 classes
        System.out.println("Total DL60 classes in one day: " + dl60Class.size());

        // Close the driver
        driver.quit();
    }
}
