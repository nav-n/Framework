package qa.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.baseTest;
import pageEvents.Browser_Events;
import pageObjects.homePageElements;
import pageObjects.loginPageElements;
import utils.Constants;
import utils.ElementFetch;
import utils.ReadData;

public class Testcase_Login extends baseTest {
	ElementFetch ele = new ElementFetch();
	Browser_Events be = new Browser_Events();

	@DataProvider(name = "loginData")
	public String[][] data() {
		return ReadData.multiple_Data_From_Excel(Constants.excelPath, "Sheet1", "TC002");
	}

	@Test(dataProvider = "loginData")
	public void login(String[] data) {
		be.Verify_If_Element_Is_Displayed(loginPageElements.shopperLoginHeading);
		be.clickOnElement(homePageElements.login);

		be.Enter_Value_In_Textfied(loginPageElements.emailTextfield, data[1]);
		be.Enter_Value_In_Textfied(loginPageElements.passwordTextfield, data[2]);
		be.clickOnElement(loginPageElements.loginButton);
	}
}
