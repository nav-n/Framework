package qa.tests;

import org.testng.annotations.Test;

import base.baseTest;
import pageEvents.Browser_Events;
import pageObjects.homePageElements;
import pageObjects.loginPageElements;
import utils.Constants;
import utils.ElementFetch;

public class Testcase1 extends baseTest {
	ElementFetch ele = new ElementFetch();
	Browser_Events be = new Browser_Events();

	@Test(testName = "methodToLogin")
	public void methodToLogin() throws InterruptedException {
		be.Wait_For_Seconds(5);
		be.clickOnElement(homePageElements.login);
		be.Verify_If_Element_Is_Displayed(loginPageElements.shopperLoginHeading);
		be.Enter_Value_In_Textfied(loginPageElements.emailTextfield, Constants.email);
		be.Enter_Value_In_Textfied(loginPageElements.passwordTextfield, Constants.Pwd);
		be.clickOnElement(loginPageElements.loginButton);

	}
}
