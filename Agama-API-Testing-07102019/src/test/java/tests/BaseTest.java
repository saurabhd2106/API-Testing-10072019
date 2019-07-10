package tests;

import java.util.Date;
import java.util.Properties;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.qa.restApi.RestClient;

import utils.ConfigFileReader;

public class BaseTest {

	String uri;
	String baseUrl;
	String userLocation;

	RestClient restClient;

	String configFilepath;

	Properties configProperties;

	String currentWorkingDirectory;

	ExtentHtmlReporter htmlReporter;
	ExtentReports extentReport;
	ExtentTest extentTest;

	String testExecutionStartTime;
	String htmlReportFilename;
	
	int statuscode_200Ok=200;
	int statuscode_201=201;

	@BeforeSuite
	public void preSetup() throws Exception {

		Date date = new Date();
		testExecutionStartTime = String.valueOf(date.getTime());

		currentWorkingDirectory = System.getProperty("user.dir");
		configFilepath = String.format("%s/config/config.properties", currentWorkingDirectory);

		configProperties = ConfigFileReader.configPropertyFileReader(configFilepath);

		setupReports();
	}

	@BeforeClass
	public void setup() throws Exception {
		extentTest = extentReport.createTest("Set up - Setting up the pre requisites for test cases");

		baseUrl = configProperties.getProperty("baseUrl");
		extentTest.log(Status.INFO, "Base URL : " + baseUrl);

		userLocation = configProperties.getProperty("userLocation");
		extentTest.log(Status.INFO, "User Location : " + userLocation);

		uri = baseUrl + userLocation;
		extentTest.log(Status.INFO, "URI : " + uri);
	}

	@AfterSuite
	public void postClean() {
		extentReport.flush();
	}

	@AfterMethod
	public void afterAMethod(ITestResult result) throws Exception {
		String testcaseName = result.getName();

		if (result.getStatus() == ITestResult.SUCCESS) {

			extentTest.pass("Test case passed : " + testcaseName);
		} else if (result.getStatus() == ITestResult.FAILURE) {

			extentTest.fail("Test case Failed : " + testcaseName);

		} else {
			extentTest.skip("Test case skipped : " + testcaseName);
		}

	}

	private void setupReports() {
		htmlReportFilename = configProperties.getProperty("htmlReportFilename");
		String htmlReportPath = String.format("%s/reports/%s_%s.html", currentWorkingDirectory, htmlReportFilename,
				testExecutionStartTime);

		htmlReporter = new ExtentHtmlReporter(htmlReportPath);
		extentReport = new ExtentReports();
		extentReport.attachReporter(htmlReporter);

	}

}
