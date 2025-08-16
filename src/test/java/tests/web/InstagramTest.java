
package tests.web;

import config.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegistrationPage;

import static config.TestListener.screenshot;
import static config.TestListener.testPassDetails;
import static org.demo.ScreenshotUtil.takeScreenshot;

public class InstagramTest extends BaseTest {

    private LoginPage page;


    @BeforeTest
    public void init() throws InterruptedException {
        driver = setupDriver("chrome");
        Thread.sleep(3000);
        page = new LoginPage(driver);
    }


    @Test(testName = "[WEB] Comments", description = "WEB")
    public void testComments() throws Exception {
        page.openLoginPage(constants.getUrl());
        page.allowCookies();
        page.enterUsername(constants.getUsername());
        page.enterPassword(constants.getPassword());
        page.submit();

        screenshot = takeScreenshot(driver,"test");

        testPassDetails = "<pre><center><b>* * * * * * * *    Comments    * * * * * * * *</b></center></br></br>"
                + "<a href='data.xlsx'>Test Data</a></br>"
                + "<center><b>Test Case</center></b></br></br>"
                + "1. Open https://demoqa.com/automation-practice-form</br>"
                + "2. Enter all mandatory details</br>"
                + "3. Submit form</br>"
                + "4. Wait for modal dialog to be displayed and verify that all data was successfully submitted"
                + "</pre>";
    }
}