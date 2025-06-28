
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
        TestListener.setDriver(driver);
        form = new RegistrationPage(driver);
        driver.manage().window().maximize();
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

        Assert.assertTrue(form.modalContainsText("Ivan"));
        Assert.assertTrue(form.modalContainsText("Petrov"));
        Assert.assertTrue(form.modalContainsText("ivan.petrov@example.com"));
        Assert.assertTrue(form.modalContainsText("Male"));
        Assert.assertTrue(form.modalContainsText("0888123456"));
        Assert.assertTrue(form.modalContainsText("Maths"));
        Assert.assertTrue(form.modalContainsText("Sports"));
        Assert.assertTrue(form.modalContainsText("123 Test Street"));

        form.closeModal();
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}