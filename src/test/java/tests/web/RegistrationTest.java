
package tests.web;

import config.BaseTest;
import config.ExtentTestNGListener;
import data.Constants;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.RegistrationPage;

import java.io.IOException;
import java.text.ParseException;

import static config.ExtentTestNGListener.*;
import static org.demo.ScreenshotUtil.screenshot;
import static org.demo.ScreenshotUtil.takeScreenshot;


@Listeners(ExtentTestNGListener.class)
public class RegistrationTest extends BaseTest {

    private RegistrationPage form;
    private Constants constants;



    @BeforeClass
    public void init() throws InterruptedException, IOException, ParseException {
        form = new RegistrationPage(driver);

        constants = new Constants();
        constants.readTestData("TestData", 1);
    }


    @Test(testName = "[WEB] Registration Form", description = "WEB")
    public void testRegistrationFormSubmission() throws Exception {
        form.openFormPage();

        form.enterFirstName(constants.getFirstName());
        form.enterLastName(constants.getLastName());
        form.enterEmail(constants.getEmail());
        form.enterAddress(constants.getAddress());
        form.enterSubject(constants.getSubject());
        form.enterMobileNumber(constants.getMobile());
        form.selectGender(constants.getGender());
        form.selectDateOfBirth(constants.getBirthDate());
        form.selectHobby(constants.getFirstHobby());
        form.selectHobby(constants.getSecondHobby());
        form.selectStateAndCity(constants.getState(), constants.getCity());

        form.submitForm();

        Assert.assertTrue(form.isConfirmationModalDisplayed(), "Modal should be displayed");

        Assert.assertTrue(form.modalContainsText(constants.getFirstName()));
        Assert.assertTrue(form.modalContainsText(constants.getLastName()));
        Assert.assertTrue(form.modalContainsText(constants.getEmail()));
        Assert.assertTrue(form.modalContainsText(constants.getGender()));
        Assert.assertTrue(form.modalContainsText(constants.getMobile()));
        Assert.assertTrue(form.modalContainsText(constants.getFirstHobby()));
        Assert.assertTrue(form.modalContainsText(constants.getSecondHobby()));
        Assert.assertTrue(form.modalContainsText(constants.getAddress()));

        screenshotName = takeScreenshot(driver, "screen");
        form.closeModal();

        testPassDetails = "<pre><center><b>* * * * * * * *    User Registration Form    * * * * * * * *</b></center></br></br>"
                + "<a href='data.xlsx'>Test Data</a></br>"
                + "<center><b>Test Case</center></b></br></br>"
                + "1. Open https://demoqa.com/automation-practice-form</br>"
                + "2. Enter all mandatory details</br>"
                + "3. Submit form</br>"
                + "4. Wait for modal dialog to be displayed and verify that all data was successfully submitted"
                + "</pre>";
    }
}