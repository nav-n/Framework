package pageEvents;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import base.baseTest;
import utils.ElementFetch;

public class Browser_Events extends baseTest {
	ElementFetch ele = new ElementFetch();

	public void clickOnElement(String element) {
		WebElement Button = ele.elements("xpath", element);
		Button.click();
		Reporter.log("Clicked on Element " + element, true);
	}

	public void Enter_Value_In_Textfied(String element, String value) {
		WebElement Field = ele.elements("xpath", element);
		Field.sendKeys(value);
		Reporter.log("Entered " + value + " Successfully", true);
	}

	public void Verify_If_Element_Is_Displayed(String element) {
		Assert.assertTrue(ele.elements("xpath", element).isDisplayed(), "Element is not Displayed");
		Reporter.log(element + " is displayed", true);
	}

	public void Verify_If_Element_Is_Enabled(String element) {
		Assert.assertTrue(ele.elements("xpath", element).isEnabled(), "Element is not Enabled");
	}

	public void select_By_Visible_Text(String element, String visibleText) {
		WebElement Field = ele.elements("xpath", element);
		Select select = new Select(Field);
		select.selectByVisibleText(visibleText);
	}

	public void select_By_Value(String element, String value) {
		WebElement Field = ele.elements("xpath", element);
		Select select = new Select(Field);
		select.selectByValue(value);
	}

	public void select_By_Index(String element, int index) {
		WebElement Field = ele.elements("xpath", element);
		Select select = new Select(Field);
		select.selectByIndex(index);
	}

	public void deselect_All(String element) {
		WebElement Field = ele.elements("xpath", element);
		Select select = new Select(Field);
		select.deselectAll();
	}

	public void get_Screenshot_Of_Element(String element, String fileName) {
		WebElement val = ele.elements("xpath", element);
		File src = val.getScreenshotAs(OutputType.FILE);
		File dest = new File("./Screenshots/elements screenshots/" + fileName);

		try {
			FileHandler.copy(src, dest);
			System.out.println("Element screenshot saved: " + dest.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Failed to save element screenshot");
			e.printStackTrace();
		}
	}

	public void Wait_For_Seconds(int time) throws InterruptedException {
		int t = time * 1000;
		Thread.sleep(t);
		Reporter.log("Waited for " + time + " Seconds", true);
	}

	public String FetchValuesFromMap(Map<String, String> map, String key) throws InterruptedException {
		try {
			String val = "";
			if (map.containsKey(key)) {
				val = map.get(key);
				Reporter.log("Fetched value from Map " + val, true);
				return val;
			} else {
				Reporter.log("Invalid Key " + key, false);
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return key;

	}

	public void Print_Values(String value) throws InterruptedException {
		System.out.println(value);
		Reporter.log("Value is: " + value, true);
	}

	public void Print_Values(int value) throws InterruptedException {
		System.out.println(value);
		Reporter.log("Value is: " + value, true);
	}

	public void Print_Values(Map value) throws InterruptedException {
		System.out.println(value);
		Reporter.log("Value is: " + value, true);
	}
	public static String CurrentDateTime(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current Date and Time: " + now);

        // Format the date/time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = now.format(formatter);
        System.out.println("Formatted Date and Time: " + formatted);
		return formatted;
    }
}