
package tests.web;

import config.BaseTest;
import config.ExtentTestNGListener;
import config.SeleniumDriverManager;
import data.TradingConstants;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.web.plus500.HomePage;
import pages.web.plus500.LoginPage;

import java.io.IOException;
import java.text.ParseException;


@Listeners(ExtentTestNGListener.class)
public class PlusCheckWebTest extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private TradingConstants constants;


    @BeforeTest
    public void setup() throws InterruptedException, IOException, ParseException {
        constants = new TradingConstants();
        constants.readTradingAccounts();

        SeleniumDriverManager.setupSeleniumDriver("chrome");
        loginPage = new LoginPage(SeleniumDriverManager.getSeleniumDriver());
        homePage = new HomePage(SeleniumDriverManager.getSeleniumDriver());
    }

    @AfterTest
    public void teardown() {
        SeleniumDriverManager.quitSeleniumDriver();
    }


    @Test(testName = "[WEB] Irish Wilds - Validate Balance Update After Spin", description = "WEB", priority = 0)
    public void trade() throws Exception {

        loginPage.loginDemo(constants.getDemoUsername(), constants.getDemoPassword());

        ExtentTestNGListener.testDetails = "<pre><center><b>* * * * * * * *    Irish Wilds    * * * * * * * *</b></center><br><br>"
                + "<a href='data.xlsx'>Test Data</a><br><br>"
                + "<b>Test Case</b><br>"
                + "1. Load Irish Wilds game</br>"
                + "2. Click Spin button<br><br>"
                + "<b>Validations</b><br>"
                + "     - Game is loaded<br>"
                + "     - Game UI loads correctly<br>"
                + "     - Balance is updated correctly after spin<br><br>"
                + "<b>Expected balance $" + "expectedBalance" + "</b>"
                + "</pre>";
    }
}