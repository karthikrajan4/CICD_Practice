package basePackage.steps;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;

import basePackage.utilities.MyLogger;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions extends ExecutionMethods {
	boolean result;
	ExtentTest extentStep;
	Properties prop = getORProp();
	WebDriver driver = getDriver();
	public String validEmailAddress = null;
	public String memberForRoleFilterCheck = null;
	public String TCName = null;
	public String scenarioName;
	public String status = null;

	@Before
	public void startScenario(Scenario Scenario) {
		Collection<String> tags = Scenario.getSourceTagNames();
		MyLogger.info("");
		MyLogger.info("");
		String[] TCNames = Scenario.getName().split("_");
		TCName = TCNames[0];
		MyLogger.info("******Start Scenario: " + Scenario.getName() + " " + tags.toString() + " : "
				+ getBrowserName().toUpperCase() + "  " + Thread.currentThread().getId() + " ******");
		scenarioName = Scenario.getName();
		scenario = extent.createTest(Scenario.getName()).assignDevice(getBrowserName().toUpperCase());
	}

	public void beforeStep() {
		MyLogger.info("");
		MyLogger.info("======Start Step: " + StepListener.stepName.get() + " : " + getBrowserName().toUpperCase() + " "
				+ Thread.currentThread().getId() + " =====");
		// if (!StepListener.stepName.get().contains("verif")) {
		// extentStep = scenario.createNode(StepListener.stepName.get());
		// }
		extentStep = scenario.createNode(StepListener.stepName.get());
		result = false;
	}

	public void afterStep(boolean result) throws Throwable {
		MyLogger.info("======End Step: " + StepListener.stepName.get() + " : " + getBrowserName().toUpperCase() + " "
				+ Thread.currentThread().getId() + " =====");
		if (!result && getDefaultProp().getProperty("FailedScreenshots").equalsIgnoreCase("Y")) {
			extentStep.addScreenCaptureFromPath(captureScreenShot(driver), StepListener.stepName.get());
		}
		logReport(result, StepListener.stepName.get(), extentStep, null);

	}

	public void afterStep(boolean result, Exception e) throws Throwable {
		MyLogger.info("======End Step: " + StepListener.stepName.get() + " : " + getBrowserName().toUpperCase() + " "
				+ Thread.currentThread().getId() + " =====");
		if (getDefaultProp().getProperty("FailedScreenshots").equalsIgnoreCase("Y")) {
			extentStep.addScreenCaptureFromPath(captureScreenShot(driver), StepListener.stepName.get());
		}
		logReport(result, StepListener.stepName.get(), extentStep, e);
	}

	@After
	public void closeScenario(Scenario Scenario) throws Exception {
		MyLogger.info("******End Scenario: " + Scenario.getName() + " : " + getBrowserName().toUpperCase() + "  "
				+ Thread.currentThread().getId() + "******");
		String scenarioStatus = scenario.getStatus().toString();
		@SuppressWarnings("unused")
		boolean scenarioStatusFinal = scenarioStatus.equalsIgnoreCase("PASS") ? true : false;
		System.out.println("==========Scenario Status " + Scenario.getStatus() + "================");
		if (Scenario.getStatus().toString().equalsIgnoreCase("UNDEFINED")) {
			System.out.println("***>> Scenario '" + Scenario.getName() + "' failed at line(s) " + Scenario.getLine()
					+ " with status '" + Scenario.getStatus() + "");
			scenario.fail("SCENARIO STATUS : " + Scenario.getStatus().toString());
		}
		// rg.get().report(scenarioStatusFinal, "", Scenario.getName());
	}

	public void logReport(boolean result, String step, ExtentTest logInfo, Exception e) {
		try {
			if (result) {
				logResult("PASS", driver, logInfo, step, e);
				Assert.assertTrue(true);
			} else if (!result) {
				logResult("FAIL", driver, logInfo, step, e);
				Assert.fail(step, e);
			}
		} catch (Exception e1) {
			logResult("FAIL", driver, logInfo, step, e1);
			Assert.fail(step, e);
		}
	}

	@Given("User launches the browser with {string} website")
	public void user_launches_the_browser_with_website(String url) throws Throwable {
		int i = 0;
		Boolean resultArr[] = new Boolean[100];
		boolean result = false;
		try {
			this.beforeStep();
			resultArr[i++] = userLoadsURL(url, extentStep);
			result = !Arrays.asList(resultArr).contains(false);
			this.afterStep(result);
		} catch (Exception e) {
			e.printStackTrace();
			this.afterStep(result, e);
		}
	}

	@Then("User clicks {string} menu")
	public void user_clicks_the_mainMenu(String mainMenu) throws Throwable {
		int i = 0;
		Boolean resultArr[] = new Boolean[100];
		boolean result = false;
		try {
			this.beforeStep();
			resultArr[i++] = user_clicks_toolsQA_mainMenu(mainMenu, extentStep);
			resultArr[i++] = user_clicks_subMenu_and_perform(extentStep);
			result = !Arrays.asList(resultArr).contains(false);
			this.afterStep(result);
		} catch (Exception e) {
			e.printStackTrace();
			this.afterStep(result, e);
		}
	}

}
