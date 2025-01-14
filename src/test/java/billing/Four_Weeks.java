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


public class Four_Weeks {

	public static void main(String[] args) throws InterruptedException {
		
		        WebDriverManager.chromedriver().setup();
		        WebDriver driver = new ChromeDriver();
		        driver.get("https://dev.schedulehub.io/");
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

		        // Map to store cumulative counts by class type for each week
		        Map<String, int[]> classTypeSummaryMap = new HashMap<>();

		        // Loop to process each week
		        for (int week = 1; week <= 4; week++) {
		            // Calculate the start and end days of the current week
		            int startDay = (week - 1) * 7 + 1;
		            int endDay = week * 7;

		            System.out.println("\nProcessing Week " + week + " (Days " + startDay + " to " + endDay + "):");

		            // Iterate through the days of the current week
		            for (int i = startDay; i <= endDay; i++) {
		                // Open calendar and select the specific day
		                WebElement calendar = driver.findElement(By.xpath("//button[contains(@class, 'MuiIconButton-root')]"));
		                calendar.click();
		                Thread.sleep(1000);

		                WebElement date = driver.findElement(By.xpath("//button[contains(@class, 'MuiPickersDay-root') and contains(text(), '" + i + "')]"));
		                date.click();
		                Thread.sleep(2000);

		                // Find all class elements for the day
		                List<WebElement> classElements = driver.findElements(By.xpath("//div[contains(@class, 'classItemHeader')]//p[contains(@class, 'classTitle')]"));

		                for (WebElement classElement : classElements) {
		                    String classText = classElement.getText();
		                    
		                    // Extract class type and ensure it's not empty
		                    String classType = classText.split(" - ")[0].trim(); // Remove leading/trailing spaces

		                    // Skip if class type is empty
		                    if (classType == null || classType.isEmpty()) {
		                        continue; 
		                    }
		                    
		                    WebElement classContainer = classElement.findElement(By.xpath("./ancestor::div[contains(@class, '_card_')]"));

		                    // Locate trial, non-trial, and makeup students within this class container
		                    List<WebElement> trialStudentElements = classContainer.findElements(By.cssSelector("div.MuiStack-root._backgroundPurple_wbyfb_451"));
		                    List<WebElement> allStudentElements = classContainer.findElements(By.xpath(".//div[contains(@class, '_hoverGrayBg_wbyfb_466')]/p[contains(@class, '_listItem_wbyfb_289') and contains(text(), 'yrs')]"));
		                    List<WebElement> makeupStudentElements = classContainer.findElements(By.className("_backgroundMakeup_wbyfb_455"));

		                    int trialCount = trialStudentElements.size();
		                    int allCount = allStudentElements.size();
		                    int makeupCount = makeupStudentElements.size();

		                    // Update counts for this class type in the map
		                    classTypeSummaryMap.putIfAbsent(classType, new int[3]); // Initialize the array if not already present
		                    classTypeSummaryMap.get(classType)[0] += allCount; // Index 0: Non-trial students
		                    classTypeSummaryMap.get(classType)[1] += trialCount;    // Index 1: Trial students
		                    classTypeSummaryMap.get(classType)[2] += makeupCount;   // Index 2: Makeup students
		                }
		            }

		            // Print weekly summary for each class type
		            System.out.println("\n[ Weekly Summary for Week " + week + " ]:");
		            for (Map.Entry<String, int[]> entry : classTypeSummaryMap.entrySet()) {
		                String classType = entry.getKey();
		                int[] counts = entry.getValue();

		                // Subtract trial and makeup counts from total students
		                int nonTrialNonMakeupCount = counts[0] - counts[1] - counts[2];

		                System.out.println("Class Type: " + classType);
		                System.out.println("  Total All Students: " + counts[0]);
		                System.out.println("  Total Trial Students: " + counts[1]);
		                System.out.println("  Total Makeup Students: " + counts[2]);
		                System.out.println("  Non-Trial, Non-Makeup Students: " + nonTrialNonMakeupCount);
		                System.out.println("---------------------------------------------");
		            }
		            // Reset the map for the next week
		            classTypeSummaryMap.clear();
		        }

		        driver.quit();
	}

}
