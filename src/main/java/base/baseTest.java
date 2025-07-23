package base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.*;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Constants;

public class baseTest {
	public static WebDriver driver;
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	public Actions actions;
	public LocalDateTime dateTime;

	@BeforeTest
	public void reports() {
		String reportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator
				+ "AutomationReport.html";
		sparkReporter = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("HostName", "RHEL8");
		extent.setSystemInfo("UserName", "root");
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Automation Testing Results");
	}

	@Parameters("browser")
	@BeforeMethod
	public void browserSetup(@Optional("chrome") String browser, Method testMethod) throws InterruptedException {
		logger = extent.createTest(testMethod.getName());
		setupDriver(browser);
		driver.manage().window().maximize();
		Reporter.log("Browser is maximized", true);
		driver.get(Constants.url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		Thread.sleep(2000);
		Reporter.log("Landed successfully on Home Page", true);
	}

	public void captureScreenshot(String fileName) {
		if (driver == null) {
			System.err.println("Driver is null. Cannot capture screenshot.");
			return;
		}
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			LocalDateTime now = LocalDateTime.now();
			System.out.println("Current Date and Time: " + now);

			// Format the date/time
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatted = now.format(formatter);
			File dest = new File("./ScreenShots/" + fileName +"_"+formatted+".png");
			//dest.getParentFile().mkdirs(); // create directory if needed
			FileUtils.copyFile(src, dest);
			System.out.println("Screenshot saved successfully: " + dest.getAbsolutePath());
		} catch (IOException e) {
			System.err.println("Failed to capture screenshot: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Testcase Failed", ExtentColor.RED));
			captureScreenshot(result.getMethod().getMethodName() + ".png");
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Testcase Skipped", ExtentColor.ORANGE));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " - Testcase Passed", ExtentColor.GREEN));
		}

		if (driver != null) {
			// driver.quit();
		}
	}

	@AfterTest
	public void afterTest() {
		extent.flush();
	}

	public void setupDriver(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			Reporter.log("Chrome Browser is Launched", true);
		} else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			Reporter.log("Edge Browser is Launched", true);
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			Reporter.log("Firefox Browser is Launched", true);
		} else {
			throw new InvalidBrowserValueException();
		}
	}

	public WebDriver getDriver() {
		return driver;
	}
}

class InvalidBrowserValueException extends RuntimeException {
	public InvalidBrowserValueException() {
		super("Invalid Browser Value");
	}

}
