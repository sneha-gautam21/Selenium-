package billing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Lesson {

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://stage.schedulehub.io/");
        driver.manage().window().maximize();

        driver.findElement(By.name("email")).sendKeys("test@gmail.com");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.className("_submitBtn_yye06_97")).click();

        Thread.sleep(4000);

        System.out.println("Login Successfully");

        Thread.sleep(3000);

        WebElement schedule = driver.findElement(By.xpath("//a[@href='#/schedule/teacherSchedule']"));
        schedule.click();

        WebDriverWait wait = new WebDriverWait(driver, (10));
        WebElement lessonSchedule = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Lesson Schedule']")));
        lessonSchedule.click();

        Thread.sleep(3000);

        // Initialize counters for total DL60 classes, students, and trial students for the entire week
        int totalDL60Count = 0;
        int totalStudentCount = 0;
        int totalTrialStudentCount = 0;

        // Iterate through the next 7 days (1 week)
        for (int i = 1; i <= 7; i++) {
            WebElement calender = driver.findElement(By.xpath("//button[contains(@class, 'MuiIconButton-root')]"));
            calender.click();

            // Find the specific date button (1, 2, 3, ..., 7)
            WebElement date = driver.findElement(By.xpath("//button[contains(@class, 'MuiPickersDay-root') and contains(text(), '" + i + "')]"));
            date.click();
            System.out.println("Selected Day: " + i);

            Thread.sleep(2000);

            // Locate all instances of the "DL60" class
            List<WebElement> classElements = driver.findElements(By.xpath("//p[contains(text(), 'DL60')]"));
            System.out.println("Number of DL60 classes on Day " + i + ": " + classElements.size());

            // Count the total number of DL60 classes for the entire week
            totalDL60Count += classElements.size();

            // Iterate over each "DL60" class element
            for (WebElement classElement : classElements) {
                List<WebElement> studentElements = classElement.findElements(By.xpath(".//ancestor::div[contains(@class, '_card_140s2_145')]//div[contains(@class, '_hoverGrayBg_140s2_458')]//p[contains(@class, '_listItem_140s2_286') and contains(text(), 'yrs')]"));

                int studentCountInThisClass = studentElements.size();
                System.out.println("Number of students in this DL60 class on Day " + i + ": " + studentCountInThisClass);

                // Accumulate the student count to the total for the entire week
                totalStudentCount += studentCountInThisClass;

                // Print each student's name
                for (WebElement student : studentElements) {
                    System.out.println("Student: " + student.getText());
                }

                // Locate trial students by identifying unique trait (like purple background or a "trial" class)
                List<WebElement> trialStudentElements = classElement.findElements(By.xpath(".//ancestor::div[contains(@class, '_card_140s2_145')]//div[contains(@class, 'backgroundPurple')]//p[contains(@class, '_listItem_140s2_286') and contains(text(), 'yrs')]"));

                int trialStudentCountInThisClass = trialStudentElements.size();
                System.out.println("Number of trial students in this DL60 class: " + trialStudentCountInThisClass);

                // Accumulate the trial student count to the total for the entire week
                totalTrialStudentCount += trialStudentCountInThisClass;

                // Print each trial student's name
                for (WebElement trialStudent : trialStudentElements) {
                    System.out.println("Trial Student: " + trialStudent.getText());
                }
            }
        }

        // After looping through all days, print the total counts for the entire week
        System.out.println("\n--- Total for the Entire Week ---");
        System.out.println("Total number of DL60 classes: " + totalDL60Count);
        System.out.println("Total number of students across all DL60 classes: " + totalStudentCount);
        System.out.println("Total number of trial students across all DL60 classes: " + totalTrialStudentCount);

        // Close the browser
        driver.quit();
    }
}
