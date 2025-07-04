
package tests;

import config.BaseTest;
import config.TestListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.RegistrationPage;

import java.text.ParseException;

public class RegistrationTest extends BaseTest {

    private WebDriver driver;
    private RegistrationPage form;


    @Parameters("browser")
    @BeforeClass
    public void setupDriver(@Optional("chrome") String browser) {
        switch (browser.toLowerCase()) {

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");

                driver = new ChromeDriver(options);
                break;
        }
        setDriver(driver);
        form = new RegistrationPage(driver);
    }



    @Test
    public void testRegistrationFormSubmission() throws ParseException {
        test = extent.createTest("Registration").assignCategory("WEB");

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

        test.pass("<pre><center><b>* * * * * * * *    User Registration Form    * * * * * * * *</b></center></br></br>"
                + "<a href='data.xlsx'>Test Data</a></br></br>"
                + "<center><b>Test Case</center></b></br></br>"
                + "1. Open https://demoqa.com/automation-practice-form</br>"
                + "2. Enter all mandatory details</br>"
                + "3. Submit form</br>"
                + "4. Wait for modal dialog to be displayed and verify that all data was successfully submitted"
                + "</pre>");

        form.closeModal();
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}