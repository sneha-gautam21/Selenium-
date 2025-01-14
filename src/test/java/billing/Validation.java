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
public class Validation {


	    // Enum for Class Names
	    public enum EnumClass {
	        DL60,
	        PL60,
	        LT30,
	        JCK60,
	        P30,
	        GL60;

	        // Method to check if a given string matches a valid enum value
	        public static boolean isValidClassName(String className) {
	            try {
	                // Try to match the class name with an enum value
	            	EnumClass.valueOf(className);
	                return true;
	            } catch (IllegalArgumentException e) {
	                // If exception occurs, it means the class name is invalid
	                return false;
	            }
	        }
	    }

	    public static void main(String[] args) throws InterruptedException {
	        // Set up WebDriver for chrome
	        WebDriverManager.chromedriver().setup();
	        WebDriver driver = new ChromeDriver();
	        driver.get("https://stage.schedulehub.io/");
	        driver.manage().window().maximize();

	        // Log in to the website
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

	        // Create a scanner to take user input for the class name
	        Scanner scanner = new Scanner(System.in);
	        String classNameInput = "";

	        // Loop until the user enters a valid class name
	        while (true) {
	            System.out.print("Enter the class name (DL60, PL60, LT30, JCK60, P30, GL60): ");
	            classNameInput = scanner.nextLine().trim().toUpperCase();

	            // Validate if the class name is valid
	            if (EnumClass.isValidClassName(classNameInput)) {
	                break;  // Exit the loop if valid class name is entered
	            } else {
	                System.out.println("Invalid class name. Please enter one of the following valid class names: DL60, PL60, LT30, JCK60, P30, GL60.");
	            }
	        }

	        // Iterate through the next 7 days (or 30 days if you want a month)
	        for (int i = 1; i <= 7; i++) {
	            WebElement calendar = driver.findElement(By.xpath("//button[contains(@class, 'MuiIconButton-root')]"));
	            calendar.click();

	            // Find the specific date button (1, 2, 3, ..., 7)
	            WebElement date = driver.findElement(By.xpath("//button[contains(@class, 'MuiPickersDay-root') and contains(text(), '" + i + "')]"));
	            date.click();
	            System.out.println("Selected Day: " + i);

	            Thread.sleep(2000);

	            // Find all class elements for the day
	            List<WebElement> classElements = driver.findElements(By.xpath("//p[contains(text(), '')]")); 
	            
	            // Filter class elements based on user input 
	            for (WebElement classElement : classElements) {
	                String className = classElement.getText();
	                // If the class name doesn't match the input, skip this element
	                if (!className.contains(classNameInput)) {
	                    continue;
	                }

	                // If valid, proceed with counting and processing
	                System.out.println("Class Name: " + className);
	                totalClassCount++;

	                // Count and display class details for the entered class name
	                List<WebElement> studentElements = classElement.findElements(By.xpath(".//ancestor::div[contains(@class, '_card_140s2_145')]//div[contains(@class, '_hoverGrayBg_140s2_458')]//p[contains(@class, '_listItem_140s2_286') and contains(text(), 'yrs')]"));
	                int studentCountInThisClass = studentElements.size();
	                System.out.println("Number of students in this " + classNameInput + " class on Day " + i + ": " + studentCountInThisClass);
	                totalStudentCount += studentCountInThisClass;

	                // Print each student's name
	                for (WebElement student : studentElements) {
	                    System.out.println("Student: " + student.getText());
	                }

	                // Locate trial students (purple background or unique trait)
	                List<WebElement> trialStudentElements = classElement.findElements(By.xpath(".//ancestor::div[contains(@class, '_card_140s2_145')]//div[contains(@class, 'backgroundPurple')]//p[contains(@class, '_listItem_140s2_286') and contains(text(), 'yrs')]"));
	                int trialStudentCountInThisClass = trialStudentElements.size();
	                System.out.println("Number of trial students in this " + classNameInput + " class: " + trialStudentCountInThisClass);
	                totalTrialStudentCount += trialStudentCountInThisClass;

	                // Print each trial student's name
	                for (WebElement trialStudent : trialStudentElements) {
	                    System.out.println("Trial Student: " + trialStudent.getText());
	                }
	            }
	        }

	        // After looping through all days, print the total counts
	        System.out.println("\n--- Total for the Entire Week ---");
	        System.out.println("Total number of " + classNameInput + " classes: " + totalClassCount);
	        System.out.println("Total number of students across all " + classNameInput + " classes: " + totalStudentCount);
	        System.out.println("Total number of trial students across all " + classNameInput + " classes: " + totalTrialStudentCount);

	        // Close the scanner and browser
	        scanner.close();
	        driver.quit();
	    }
	}


