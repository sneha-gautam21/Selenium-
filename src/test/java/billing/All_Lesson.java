package billing;
import java.util.List;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class All_Lesson {

    public static void main(String[] args) throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://stage.schedulehub.io/");
        driver.manage().window().maximize();

        // Login to the application
        driver.findElement(By.name("email")).sendKeys("test@gmail.com");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.className("_submitBtn_yye06_97")).click();

        Thread.sleep(4000);

        System.out.println("Login Successfully");
        Thread.sleep(3000);

        // Navigate to the schedule page
        WebElement schedule = driver.findElement(By.xpath("//a[@href='#/schedule/teacherSchedule']"));
        schedule.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement lessonSchedule = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Lesson Schedule']")));
        lessonSchedule.click();

        Thread.sleep(3000);

        // Initialize counters
        int totalClassCount = 0;
        int totalStudentCount = 0;
        int totalTrialStudentCount = 0;
        int totalMakeupStudentCount = 0;  
        int totalRegularStudentCount = 0;  


        // Create a scanner to take user input for the class name
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the class name: ");
        String classNameInput = scanner.nextLine().trim();

        // Iterate through the next 7 days 
        for (int i = 1; i <= 7; i++) {
            WebElement calendar = driver.findElement(By.xpath("//button[contains(@class, 'MuiIconButton-root')]"));
            calendar.click();

            // Find the specific date button (1, 2, 3, ..., 7)
            WebElement date = driver.findElement(By.xpath("//button[contains(@class, 'MuiPickersDay-root') and contains(text(), '" + i + "')]"));
            date.click();
            System.out.println("Selected Day: " + i);

            Thread.sleep(2000);

            // Find all class elements for the day
            List<WebElement> classElements = driver.findElements(By.xpath("//p[contains(text(), '')]//ancestor::div[contains(@class, '_card_')]"));

            // Filter class elements based on user input
            classElements.removeIf(classElement -> !classElement.getText().contains(classNameInput));

            // Count and display class details for the entered class name
            System.out.println("Number of " + classNameInput + " classes on Day " + i + ": " + classElements.size());
            totalClassCount += classElements.size();

            // Iterate over each class element that matches the user input
            for (WebElement classElement : classElements) {
                // Find all students in the class
                List<WebElement> studentElements = classElement.findElements(By.cssSelector("div.MuiStack-root._hoverGrayBg_wbyfb_466._cursorPointer_wbyfb_488.css-1y3ojfh p.MuiTypography-body1"));

                int studentCountInThisClass = studentElements.size();
                System.out.println("Number of students in this " + classNameInput + " class on Day " + i + ": " + studentCountInThisClass);

                totalStudentCount += studentCountInThisClass;

                // Print each student's name
                for (WebElement student : studentElements) {
                    System.out.println("Student: " + student.getText());
                }

                // Locate trial students (assuming purple background for trial students)
                List<WebElement> trialStudentElements = classElement.findElements(By.xpath(".//div[contains(@class, '_backgroundPurple_mwqdo_448')]//p[contains(@class, '_listItem_mwqdo_286') and contains(text(), 'yrs')]"));

                int trialStudentCountInThisClass = trialStudentElements.size();
                System.out.println("Number of trial students in this " + classNameInput + " class: " + trialStudentCountInThisClass);

                totalTrialStudentCount += trialStudentCountInThisClass;

                // Print each trial student's name
                for (WebElement trialStudent : trialStudentElements) {
                    System.out.println("Trial Student: " + trialStudent.getText());
                }

                // Locate makeup students (assuming orange background for makeup students)
                List<WebElement> makeupStudentElements = classElement.findElements(By.xpath(".//div[contains(@class, '_backgroundMakeup_mwqdo_452')]//p[contains(@class, '_listItem_mwqdo_286') and contains(text(), 'yrs')]"));

                int makeupStudentCountInThisClass = makeupStudentElements.size();
                System.out.println("Number of makeup students in this " + classNameInput + " class: " + makeupStudentCountInThisClass);

                totalMakeupStudentCount += makeupStudentCountInThisClass;

                // Print each makeup student's name
                for (WebElement makeupStudent : makeupStudentElements) {
                    System.out.println("Makeup Student: " + makeupStudent.getText());
                }
            }
        }
        
     // Calculate regular students (students that are neither trial nor makeup)
        totalRegularStudentCount = totalStudentCount - totalTrialStudentCount - totalMakeupStudentCount;

        // After looping through all days, print the total counts
        System.out.println("\n--- Total for the Entire Week ---");
        System.out.println("Total number of " + classNameInput + " classes: " + totalClassCount);
        System.out.println("Total number of students across all " + classNameInput + " classes: " + totalStudentCount);
        System.out.println("Total number of trial students across all " + classNameInput + " classes: " + totalTrialStudentCount);
        System.out.println("Total number of makeup students across all " + classNameInput + " classes: " + totalMakeupStudentCount);
        System.out.println();
        System.out.println("Total number of regular students (non-trial, non-makeup) across all " + classNameInput + " classes: " + totalRegularStudentCount);

        // Close the scanner and browser
        scanner.close();
       // driver.quit();
    }
}
