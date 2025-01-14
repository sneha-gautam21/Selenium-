package billing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Weekly_Lesson {

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

        Thread.sleep(3000); // Wait for page to load completely

        // Maps to store the count of trial and non-trial students for each class type
        Map<String, Integer> totalStudentsByClassType = new HashMap<>();
        Map<String, Integer> trialStudentsByClassType = new HashMap<>();

        // Find all class elements for the day
        List<WebElement> classElements = driver.findElements(By.xpath("//div[contains(@class, 'classItemHeader')]//p[contains(@class, 'classTitle')]"));

        for (WebElement classElement : classElements) {
            String classText = classElement.getText();
            String classType = classText.split(" - ")[0];  // Extract class type (e.g., GL60, PL60)
            WebElement classContainer = classElement.findElement(By.xpath("./ancestor::div[contains(@class, '_card_')]"));
            
            // Locate trial students within this class container
            List<WebElement> trialStudentElements = classContainer.findElements(By.cssSelector("div.MuiStack-root._backgroundPurple_wbyfb_451"));
            
            // Locate non-trial students within this class container
            List<WebElement> allStudentElements = classContainer.findElements(By.xpath(".//div[contains(@class, '_hoverGrayBg_wbyfb_466')]/p[contains(@class, '_listItem_wbyfb_289') and contains(text(), 'yrs')]"));

            int trialCount = trialStudentElements.size();
            int totalCount = allStudentElements.size();

            // Update the maps with the counts for this class type
            totalStudentsByClassType.put(classType, totalStudentsByClassType.getOrDefault(classType, 0) + totalCount);
            trialStudentsByClassType.put(classType, trialStudentsByClassType.getOrDefault(classType, 0) + trialCount);

            // Print class name and student counts for this specific class instance
            System.out.println("Class: " + classText);
            System.out.println("Total Students: " + totalCount);
            System.out.println("Trial Students: " + trialCount);
            System.out.println("==============================================");
        }

        // Print cumulative counts for each class type
        System.out.println();
        System.out.println("[ Summary of Students Across All Class Types ]:");
        System.out.println();

        for (String classType : totalStudentsByClassType.keySet()) {
            System.out.println("Class Type: " + classType);
            System.out.println("  Total Students: " + totalStudentsByClassType.get(classType));
            System.out.println("  Trial Students: " + trialStudentsByClassType.getOrDefault(classType, 0));
            System.out.println("---------------------------------------------");
        }

        driver.quit();
    }
}
