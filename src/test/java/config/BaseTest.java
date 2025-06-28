package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import data.Constants;
import org.apache.commons.io.FileUtils;
import org.demo.Utilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class BaseTest {

    protected static ExtentReports extent;
    protected static ExtentTest test;
    protected static Constants constants;


    @BeforeSuite
    public void setupReport() throws IOException, ParseException {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Automation Tester");

        constants = new Constants();
        constants.readTestData("TestData", 1);
    }


    @AfterSuite
    public void tearDownReport() throws IOException {
        extent.flush();

        String folder = "test-output";
        String file   = "test-output-archive/" + Utilities.getDate() + ".zip";
        Utilities.zip(folder, file);

        FileUtils.forceMkdir(new File(folder));
    }
}
