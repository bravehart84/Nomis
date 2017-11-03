package page.tests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import page.classes.HomepageFactory;
import utilities.ExtentManager;
import utilities.Screenshots;
import utilities.WaitUtil;

public class TestHomepage {

	WebDriver driver;

	ExtentReports extent;

	HomepageFactory homepage;

	ExtentTest test;

	@AfterMethod
	public void result(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			String path = Screenshots.takeScreenshot(this.driver, testResult.getName());
			this.test.log(Status.FAIL, "Snapshot below: " + this.test.addScreenCaptureFromPath(path));
		}

	}

	@BeforeClass
	public void setUp() {

		this.driver = new FirefoxDriver();
		// this.driver = new InternetExplorerDriver();

		// this.driver.manage().window().maximize();
		// this.driver.manage().window().setSize(new Dimension(1500, 1080));

		this.extent = ExtentManager.GetExtent("QA Automation Report", "Regression Cycle");
		this.test = this.extent.createTest("Nomis Home page Tests", "Testing Search Functionality");
		this.driver.get("https://www.nomissolutions.com/");
		this.test.log(Status.INFO, "Navigated to Nomis Home Page");

		this.homepage = new HomepageFactory(this.driver);

		this.driver.manage().timeouts().pageLoadTimeout(8000, TimeUnit.SECONDS);

	}

	@AfterClass
	public void tearDown() {

		this.extent.flush();
		this.driver.close();
		// this.driver.quit();

	}

	@Test(priority = 1)
	public void testAboutNomisLink() {

		this.test = this.extent.createTest("Nomis Home page Tests", "Testing 'About Nomis' Link");
		this.homepage.clickAboutMenu();
		this.test.log(Status.INFO, "Clicked on About Nomis Link");

		try {
			WebElement whoWeAre = WaitUtil.getWhenVisible(this.driver, By.id("hs_cos_wrapper_module_14732906624785128"),
					5);

			String currentUrl = this.driver.getCurrentUrl();

			Assert.assertEquals(currentUrl, "https://www.nomissolutions.com/about");
			Assert.assertTrue(whoWeAre.isDisplayed());

			this.test.log(Status.INFO, "'Who we are' statement is displayed");

			this.homepage.clickHome();
			this.test.log(Status.INFO, "Returned to Nomis Home page");

			this.test.log(Status.PASS, "Verified 'About Nomis' Page Link is working");

		} catch (Exception e) {

			this.test.log(Status.FAIL, "Could not verify that 'About Nomis' Page Link is working");

		}

	}

	@Test(priority = 2)
	public void testRequestDemo() {

		this.test = this.extent.createTest("Nomis Home page Tests", "Testing 'Request Demo' Link");

		this.homepage.requestDemo();

		this.test.log(Status.INFO, "Clicked on Request Demo Link");

		try {

			WebElement requestForm = WaitUtil.getWhenVisible(this.driver,
					By.id("hs_cos_wrapper_module_14273976952109892"), 6);

			String currentUrl = this.driver.getCurrentUrl();

			// Verify that we're on the Request demo page and the Request form
			// is displayed.
			Assert.assertEquals(currentUrl,
					"https://info.nomissolutions.com/get-a-demo?hsCtaTracking=c71349a6-f5ad-4d47-b28a-f495f7462dd2%7Ca9f4f1b1-fd67-4c28-a8e5-8511328769eb");
			Assert.assertTrue(requestForm.isDisplayed());

			this.test.log(Status.INFO, "Request form is displayed");

			this.homepage.clickHome();

			this.test.log(Status.INFO, "Returned to Nomis Home page");

			this.test.log(Status.PASS, "Verified Request Demo Link is working and Request Form is present");

		} catch (Exception e) {
			this.test.log(Status.FAIL, "Could not verify that Request Demo Link is working");
		}
	}

	@Test(priority = 0)
	public void testSearchFunction() {

		String searchString = "products";
		this.homepage.searchItem(searchString);
		this.test.log(Status.INFO, "Searched for " + searchString + " in Custom Search input box.");
		try {

			WebElement result = WaitUtil.getWhenVisible(this.driver, By.id("gsc-i-id2"), 6);

			String currentUrl = this.driver.getCurrentUrl();

			Assert.assertEquals(currentUrl, "https://www.nomissolutions.com/search?q=" + searchString);
			Assert.assertTrue(result.isDisplayed());

			this.homepage.clickHome();

			this.test.log(Status.INFO, "Returned to Nomis Home page");

			this.test.log(Status.PASS, "Verified correct URL and Search Results");
		} catch (Exception e) {
			this.test.log(Status.FAIL, "Could not verify correct Search Results");
		}

	}

}
