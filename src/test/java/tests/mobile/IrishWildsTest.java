
package tests.mobile;

import config.BaseTest;
import config.DriverManager;
import config.ExtentTestNGListener;
import data.Constants;
import data.Endpoints;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.IrishWildPage;
import pages.RegistrationPage;

import java.io.IOException;
import java.text.ParseException;


@Listeners(ExtentTestNGListener.class)
public class IrishWildsTest extends BaseTest {

    private IrishWildPage gamePage;
    private Constants constants;



    @BeforeClass
    public void init() throws InterruptedException, IOException, ParseException {
        DriverManager.startAppiumServer();
        DriverManager.startAndroidEmulator();
        DriverManager.setupAppiumDriver();

        gamePage = new IrishWildPage(DriverManager.getAppiumDriver());

        constants = new Constants();
        constants.readTestData("TestData", 1);
    }


    @Test(testName = "[MOBILE] Irish Wilds Test", description = "MOBILE")
    public void irishWildsTest() throws Exception {
        gamePage.openIrishWildGamePage();

        ExtentTestNGListener.testPassDetails = "<pre><center><b>* * * * * * * *    Irish Wild    * * * * * * * *</b></center></br></br>"
                + "<a href='data.xlsx'>Test Data</a></br>"
                + "<center><b>Test Case</center></b></br></br>"
                + "1. Open https://demoqa.com/automation-practice-form</br>"
                + "2. Enter all mandatory details</br>"
                + "3. Submit form</br>"
                + "4. Wait for modal dialog to be displayed and verify that all data was successfully submitted"
                + "</pre>";
    }
}