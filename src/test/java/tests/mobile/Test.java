
package tests.mobile;

import config.Appium;
import config.BaseTest;
import config.ExtentTestNGListener;
import data.Constants;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import pages.RegistrationPage;

import java.io.IOException;
import java.text.ParseException;


@Listeners(ExtentTestNGListener.class)
public class Test extends BaseTest {

    private RegistrationPage form;
    private Constants constants;
    private AppiumDriver appiumDriver;



    @BeforeClass
    public void init() throws InterruptedException, IOException, ParseException {
        form = new RegistrationPage(driver);
        constants = new Constants();
        constants.readTestData("TestData", 1);
        Appium.startServer();
        appiumDriver = Appium.initAppium();
    }


    @org.testng.annotations.Test(testName = "[WEB] Registration Form", description = "WEB")
    public void testRegistrationFormSubmission() throws Exception {
        appiumDriver.get("https://demoqa.com/automation-practice-form");

        ExtentTestNGListener.testPassDetails = "<pre><center><b>* * * * * * * *    User Registration Form    * * * * * * * *</b></center></br></br>"
                + "<a href='data.xlsx'>Test Data</a></br>"
                + "<center><b>Test Case</center></b></br></br>"
                + "1. Open https://demoqa.com/automation-practice-form</br>"
                + "2. Enter all mandatory details</br>"
                + "3. Submit form</br>"
                + "4. Wait for modal dialog to be displayed and verify that all data was successfully submitted"
                + "</pre>";
    }
}