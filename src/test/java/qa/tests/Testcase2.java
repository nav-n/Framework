package qa.tests;

import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.baseTest;
import pageEvents.Browser_Events;
import pageObjects.homePageElements;
import pageObjects.loginPageElements;
import utils.Constants;
import utils.ElementFetch;
import utils.ReadData;

public class Testcase2 extends baseTest {
	ElementFetch ele = new ElementFetch();
	Browser_Events be = new Browser_Events();

	

	@Test
	public void login() throws InterruptedException {
//		be.clickOnElement(homePageElements.login);
//		be.Verify_If_Element_Is_Displayed(loginPageElements.shopperLoginHeading);
		Map<String, String> val = ReadData.ReadDataFromExcelToMap(Constants.excelPath, "Sheet1", "TC002");
		be.Print_Values(val);
		String un = be.FetchValuesFromMap(val, "UserName");
		be.Print_Values(un);
		String pwd = be.FetchValuesFromMap(val, "Pwd");
		be.Print_Values(pwd);
//		be.Enter_Value_In_Textfied(loginPageElements.emailTextfield, un);
//		be.Enter_Value_In_Textfied(loginPageElements.passwordTextfield, pwd);
//		be.clickOnElement(loginPageElements.loginButton);
	}
}
