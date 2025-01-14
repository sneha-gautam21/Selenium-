
package billing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Thirty_days {

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

	        // Map to store student names and their occurrence count for the entire month
	        Map<String, Integer> studentCountMap = new HashMap<>();

	        // Counter for total student entries across all days
	        int totalStudents = 0;

	        // Maps to store daily counts (including duplicates and unique counts)
	        Map<String, Integer> dailyTotalCountMap = new HashMap<>();
	        Map<String, Integer> dailyUniqueCountMap = new HashMap<>();

	        // Set to store filtered unique students (excluding trial students)
	        Set<String> trialStudents = new HashSet<>();
	        Set<String> makeupStudents = new HashSet<>();  // Set to track makeup students

	        // Iterate through the next 30 days
	        for (int i = 1; i <= 30; i++) {

	            // Click on the calendar icon to open the date picker
	            WebElement calendar = driver.findElement(By.xpath("//button[contains(@class, 'MuiIconButton-root')]"));
	            calendar.click();
	            
	            Thread.sleep(1000);
	            
	            // Find the specific date button (1, 2, 3, ..., 30)
	            // Adjust XPath to ensure it handles both single and double-digit days (e.g., 1, 2, 10, 11, etc.)
	            WebElement date = driver.findElement(By.xpath("//button[contains(@class, 'MuiPickersDay-root') and (text()='" + i + "' or text()='" + String.format("%02d", i) + "')]"));
	            date.click();

	            Thread.sleep(2000);

	            // Locate all student name elements for the selected date
	            List<WebElement> students = driver.findElements(By.xpath("//p[contains(@class, 'MuiTypography-root') and contains(@class, '_listItem_mwqdo_286') and contains(text(), 'yrs')]"));

	            // Set to keep track of unique students for the day
	            Set<String> dailyUniqueStudents = new HashSet<>();

	            // Count of students (including duplicates) for the current day
	            int dailyTotalCount = 0;

	            // Update the student count map and total student count
	            for (WebElement student : students) {
	                String studentName = student.getText().trim();
	                studentCountMap.put(studentName, studentCountMap.getOrDefault(studentName, 0) + 1);
	                dailyUniqueStudents.add(studentName); // Add to the daily unique set
	                dailyTotalCount++; // Increment daily total count
	                totalStudents++;   // Increment overall total count

	                // Check if the student is a trial student based on XPath
	                WebElement parentElement = student.findElement(By.xpath("..")); // Get the parent element
	                if (parentElement.getAttribute("class").contains("_backgroundPurple_mwqdo_448")) {
	                    trialStudents.add(studentName); // Add to trial students set
	                }

	                // Check if the student is a makeup student based on XPath
	                if (parentElement.getAttribute("class").contains("_backgroundMakeup_mwqdo_452")) {
	                    makeupStudents.add(studentName); // Add to makeup students set
	                }
	                
	                // Alternatively, check if the student's name contains the text "(Makeup)".
	                if (studentName.contains("(Makeup)")) {
	                    makeupStudents.add(studentName); // Add to makeup students set
	                }
	            }

	            // Store the unique and total student counts for the day
	            dailyUniqueCountMap.put("Day " + i, dailyUniqueStudents.size());
	            dailyTotalCountMap.put("Day " + i, dailyTotalCount);
	        }

	        // Calculate the number of unique students (size of the map)
	        int uniqueStudents = studentCountMap.size();

	        // Remove trial and makeup students from the unique students set
	        Set<String> filteredUniqueStudents = new HashSet<>(studentCountMap.keySet());
	        filteredUniqueStudents.removeAll(trialStudents);
	        filteredUniqueStudents.removeAll(makeupStudents);

	        // Calculate the unique students excluding trial and makeup students
	        int uniqueStudentsExcludingTrialAndMakeup = filteredUniqueStudents.size();

	        // Print the student count results for each day
	        System.out.println("Daily Student Counts:");
	        for (int i = 1; i <= 30; i++) {
	            String day = "Day " + i;
	            System.out.println(day + " - Total Students (including duplicates): " + dailyTotalCountMap.get(day));
	            System.out.println(day + " - Unique Students: " + dailyUniqueCountMap.get(day));
	        }

	        // Print the student count results for the entire month
	        System.out.println("\nStudent Occurrences over the month:");
	        for (Map.Entry<String, Integer> entry : studentCountMap.entrySet()) {
	            System.out.println(entry.getKey() + ": " + entry.getValue());
	        }

	        // Print total and unique student counts for the month
	        System.out.println("\nTotal student entries  (including duplicates): " + totalStudents);
	        System.out.println("Unique students : " + uniqueStudents);

	        // Print the unique students excluding trial and makeup
	        System.out.println("\nUnique Students excluding Trial and Makeup:");
	        System.out.println("Unique Students: " + uniqueStudentsExcludingTrialAndMakeup);

	        // Print trial students
	        System.out.println("\nTrial Students:");
	        for (String trialStudent : trialStudents) {
	            System.out.println(trialStudent);
	        }

	        // Print makeup students
	        System.out.println("\nMakeup Students:");
	        for (String makeupStudent : makeupStudents) {
	            System.out.println(makeupStudent);
	        }

	        driver.quit();
	    }
	}


