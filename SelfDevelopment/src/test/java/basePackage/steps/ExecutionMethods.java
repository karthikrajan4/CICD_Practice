package basePackage.steps;

import java.util.Arrays;

import org.openqa.selenium.TimeoutException;

import com.aventstack.extentreports.ExtentTest;

import basePackage.utilities.ITestListenerImpl;
import basePackage.utilities.MyLogger;

public class ExecutionMethods extends CommonFunctions {

	public boolean userLoadsURL(String url, ExtentTest logInfo) throws Exception {
		boolean result = false;
		try {
			getDriver().get(prop.getProperty(url));
			MyLogger.info("Launched URL: " + prop.getProperty(url));
			logInfo.addScreenCaptureFromPath(captureScreenShot(getDriver()), "test environment launched");
			result = true;
		} catch (TimeoutException e) {
			e.printStackTrace();
			getDriver().navigate().refresh();
			getDriver().get(prop.getProperty(url));
			logInfo.addScreenCaptureFromPath(captureScreenShot(getDriver()), "test environment launched");
			MyLogger.info("Launched URL: " + prop.getProperty(url));
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean user_clicks_toolsQA_mainMenu(String mainMenu, ExtentTest logInfo) throws Throwable {
		boolean result = false;
		Boolean resultArr[] = new Boolean[100];
		int i = 0;
		try {
			resultArr[i++] = click("sel_mainMenu_alertsAndFramesAndWindows", logInfo);
			resultArr[i++] = click("sel_subMenu_alertsAndFramesAndWindows_browserWindowTab", logInfo);
			result = !Arrays.asList(resultArr).contains(false);
			return result;
		} catch (

		Exception e) {
			MyLogger.info("Exception occurred : " + e.getMessage());
			e.printStackTrace();
			throw e;
		}

	}

	public boolean user_clicks_subMenu_and_perform(ExtentTest logInfo) throws Throwable {
		boolean result = false;
		Boolean resultArr[] = new Boolean[100];
		int i = 0;
		try {
			resultArr[i++] = click("sel_subMenu_alertsAndFramesAndWindows_browserWindowTab", logInfo);
			waitFor(500, logInfo);
			resultArr[i++] = click("sel_subMenu_alertsAndFramesAndWindows_newTabButton", logInfo);
			resultArr[i++] = switchToChildWindow(logInfo);
			resultArr[i++] = closeChildWindow(logInfo);
			result = !Arrays.asList(resultArr).contains(false);
			return result;
		} catch (Exception e) {
			MyLogger.info("Exception occurred : " + e.getMessage());
			e.printStackTrace();
			throw e;
		}

	}
}