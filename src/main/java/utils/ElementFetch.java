package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.baseTest;

public class ElementFetch {
	public WebElement elements(String type, String value) {

		switch (type) {
		case "xpath":
			return baseTest.driver.findElement(By.xpath(value));

		case "css":
			return baseTest.driver.findElement(By.cssSelector(value));

		case "id":
			return baseTest.driver.findElement(By.id(value));
		case "name":
			return baseTest.driver.findElement(By.name(value));
		
		case "linkText":
			return baseTest.driver.findElement(By.linkText(value));

		default:
			return null;

		}

	}

}
