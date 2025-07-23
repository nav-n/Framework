package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

import base.baseTest;


public class SuiteListener extends baseTest implements ITestListener {

	public void onTestFailure(ITestResult result) {
		
		captureScreenshot(result.getMethod().getMethodName()+".png");

	}
}
