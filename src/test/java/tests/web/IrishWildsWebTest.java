
package tests.web;

import config.BaseTest;
import config.drivers.SeleniumDriverManager;
import config.ExtentTestNGListener;
import data.Constants;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.web.IrishWildWebPage;
import utils.Calculations;

import java.io.IOException;
import java.text.ParseException;


@Listeners(ExtentTestNGListener.class)
public class IrishWildsWebTest extends BaseTest {

    private IrishWildWebPage gamePage;
    private Constants constants;


    @BeforeTest
    public void setup() throws InterruptedException, IOException, ParseException {
        constants = new Constants();
        constants.readTestData("TestData", 1);

        SeleniumDriverManager.setupSeleniumDriver("chrome");
        gamePage = new IrishWildWebPage(SeleniumDriverManager.getSeleniumDriver());
    }

    @AfterTest
    public void teardown() {
        SeleniumDriverManager.quitSeleniumDriver();
    }


    @Test(testName = "[WEB] Irish Wilds - Validate Balance Update After Spin", description = "WEB", priority = 0)
    public void balanceUpdateAfterSpin() throws Exception {
        gamePage.openIrishWildGamePage();
        gamePage.clickPlayGame();

        constants.setBalanceBefore(gamePage.getBalance());
        constants.setStake(gamePage.getStakeAmount());
        constants.setLastWinBefore(gamePage.getLastWinAmount());

        gamePage.clickSpinButton();

        constants.setBalanceAfter(gamePage.getBalance());
        constants.setLastWinAfter(gamePage.getLastWinAmount());

        String expectedBalance = Calculations.calculateBalanceAfterSingleSpin(constants.getBalanceBefore(), constants.getStake(), constants.getLastWinAfter()).toString();

        ExtentTestNGListener.testDetails = "<pre><center><b>* * * * * * * *    Irish Wilds    * * * * * * * *</b></center><br><br>"
                + "<a href='data.xlsx'>Test Data</a><br><br>"
                + "<b>Test Case</b><br>"
                + "1. Load Irish Wilds game</br>"
                + "2. Click Spin button<br><br>"
                + "<b>Validations</b><br>"
                + "     - Game is loaded<br>"
                + "     - Game UI loads correctly<br>"
                + "     - Balance is updated correctly after spin<br><br>"
                + "Stake:    " + constants.getStake()          + "<br><br>"
                + "<b>Before Spin Values</b><br>"
                + "Balance:  " + constants.getBalanceBefore()  + "<br>"
                + "Last Win: " + constants.getLastWinBefore()  + "<br><br>"
                + "<b>After Spin Values</b><br>"
                + "Balance:  " + constants.getBalanceAfter()  + "<br>"
                + "Last Win: " + constants.getLastWinAfter()  + "<br><br>"
                + "<b>Expected balance $" + expectedBalance + "</b>"
                + "</pre>";

        Assert.assertEquals(constants.getBalanceAfter().replace("$", "").replace(",", ""), expectedBalance);
    }


    @Test(testName = "[WEB] Irish Wilds - Validate Balance Update After Win", description = "WEB", priority = 1)
    public void balanceUpdateAfterWin() throws Exception {
        gamePage.openIrishWildGamePage();
        gamePage.clickPlayGame();

        constants.setBalanceBefore(gamePage.getBalance());
        constants.setStake(gamePage.getStakeAmount());
        constants.setLastWinBefore(gamePage.getLastWinAmount());

        int numberOfSpins = gamePage.spinUntilWin();

        constants.setBalanceAfter(gamePage.getBalance());
        constants.setLastWinAfter(gamePage.getLastWinAmount());

        String expectedBalance = Calculations.calculateBalanceAfterMultipleSpins(constants.getBalanceBefore(), constants.getStake(), numberOfSpins, constants.getLastWinAfter()).toString();

        ExtentTestNGListener.testDetails = "<pre><center><b>* * * * * * * *    Irish Wilds    * * * * * * * *</b></center><br><br>"
                + "<a href='data.xlsx'>Test Data</a><br><br>"
                + "<b>Test Case</b><br>"
                + "1. Load Irish Wilds game</br>"
                + "2. Click Spin button<br><br>"
                + "<b>Validations</b><br>"
                + "     - Game is loaded<br>"
                + "     - Game UI loads correctly<br>"
                + "     - Balance is updated after win<br><br>"
                + "Stake:    " + constants.getStake()          + "<br><br>"
                + "<b>Before Spin Values</b><br>"
                + "Balance:  " + constants.getBalanceBefore()  + "<br>"
                + "Last Win: " + constants.getLastWinBefore()  + "<br><br>"
                + "<b>After Spin Values</b><br>"
                + "Balance:  " + constants.getBalanceAfter()  + "<br>"
                + "Last Win: " + constants.getLastWinAfter()  + "<br><br>"
                + "</pre>";

        Assert.assertEquals(constants.getBalanceAfter().replace("$", "").replace(",", ""), expectedBalance);
    }


    @Test(testName = "[WEB] Irish Wilds - Validate Decreased Stake Change", description = "WEB", priority = 2)
    public void spinWithDecreasedStake() throws Exception {
        gamePage.openIrishWildGamePage();
        gamePage.clickPlayGame();

        gamePage.clickDecreaseStake();

        constants.setBalanceBefore(gamePage.getBalance());
        constants.setLastWinBefore(gamePage.getLastWinAmount());
        constants.setStake(gamePage.getStakeAmount());

        gamePage.clickSpinButton();

        constants.setBalanceAfter(gamePage.getBalance());
        constants.setLastWinAfter(gamePage.getLastWinAmount());

        String currentStake = gamePage.getStakeAmount();
        String expectedBalance = Calculations.calculateBalanceAfterSingleSpin(constants.getBalanceBefore(), constants.getStake(), constants.getLastWinAfter()).toString();

        ExtentTestNGListener.testDetails = "<pre><center><b>* * * * * * * *    Irish Wilds    * * * * * * * *</b></center><br><br>"
                + "<a href='data.xlsx'>Test Data</a><br><br>"
                + "<b>Test Case</b><br>"
                + "1. Load Irish Wilds game<br>"
                + "2. Decrease stake<br>"
                + "3. Click Spin button<br><br>"
                + "<b>Validations</b><br>"
                + "     - Game is loaded<br>"
                + "     - Game UI loads correctly<br>"
                + "     - Balance is updated after win<br><br>"
                + "Stake:    " + constants.getStake()          + "<br><br>"
                + "<b>Before Spin Values</b><br>"
                + "Balance:  " + constants.getBalanceBefore()  + "<br>"
                + "Last Win: " + constants.getLastWinBefore()  + "<br><br>"
                + "<b>After Spin Values</b><br>"
                + "Balance:  " + constants.getBalanceAfter()  + "<br>"
                + "Last Win: " + constants.getLastWinAfter()  + "<br><br>"
                + "</pre>";

        Assert.assertEquals(currentStake, constants.getStake());
        Assert.assertEquals(constants.getBalanceAfter().replace("$", "").replace(",", ""), expectedBalance);
    }

    @Test(testName = "[WEB] Irish Wilds - Validate Increased Stake Change", description = "WEB", priority = 3)
    public void spinWithIncreasedStake() throws Exception {
        gamePage.openIrishWildGamePage();
        gamePage.clickPlayGame();

        gamePage.clickIncreaseStake();

        constants.setBalanceBefore(gamePage.getBalance());
        constants.setLastWinBefore(gamePage.getLastWinAmount());
        constants.setStake(gamePage.getStakeAmount());

        gamePage.clickSpinButton();

        constants.setBalanceAfter(gamePage.getBalance());
        constants.setLastWinAfter(gamePage.getLastWinAmount());

        String currentStake = gamePage.getStakeAmount();
        String expectedBalance = Calculations.calculateBalanceAfterSingleSpin(constants.getBalanceBefore(), constants.getStake(), constants.getLastWinAfter()).toString();

        ExtentTestNGListener.testDetails = "<pre><center><b>* * * * * * * *    Irish Wilds    * * * * * * * *</b></center><br><br>"
                + "<a href='data.xlsx'>Test Data</a><br><br>"
                + "<b>Test Case</b><br>"
                + "1. Load Irish Wilds game<br>"
                + "2. Increase stake<br>"
                + "3. Click Spin button<br><br>"
                + "<b>Validations</b><br>"
                + "     - Game is loaded<br>"
                + "     - Game UI loads correctly<br>"
                + "     - Balance is updated after win<br><br>"
                + "Stake:    " + constants.getStake()          + "<br><br>"
                + "<b>Before Spin Values</b><br>"
                + "Balance:  " + constants.getBalanceBefore()  + "<br>"
                + "Last Win: " + constants.getLastWinBefore()  + "<br><br>"
                + "<b>After Spin Values</b><br>"
                + "Balance:  " + constants.getBalanceAfter()  + "<br>"
                + "Last Win: " + constants.getLastWinAfter()  + "<br><br>"
                + "</pre>";

        Assert.assertEquals(currentStake, constants.getStake());
        Assert.assertEquals(constants.getBalanceAfter().replace("$", "").replace(",", ""), expectedBalance);
    }

    @Test(testName = "[WEB] Irish Wilds - Validate Amounts After Refresh", description = "WEB", priority = 4)
    public void checkAmountsAfterRefresh() throws Exception {
        gamePage.openIrishWildGamePage();
        gamePage.clickPlayGame();

        constants.setBalanceBefore(gamePage.getBalance());
        constants.setLastWinBefore(gamePage.getLastWinAmount());
        constants.setStake(gamePage.getStakeAmount());

        gamePage.clickSpinButton();

        constants.setBalanceAfter(gamePage.getBalance());
        constants.setLastWinAfter(gamePage.getLastWinAmount());


        gamePage.refreshGamePage();
        gamePage.clickPlayGame();

        String balanceAfterRefresh = gamePage.getBalance();
        String lastWinAfterRefresh = gamePage.getLastWinAmount();
        String stakeAfterRefresh = gamePage.getStakeAmount();

        ExtentTestNGListener.testDetails = "<pre><center><b>* * * * * * * *    Irish Wilds    * * * * * * * *</b></center><br><br>"
                + "<a href='data.xlsx'>Test Data</a><br><br>"
                + "<b>Test Case</b><br>"
                + "1. Load Irish Wilds game<br>"
                + "2. Increase stake<br>"
                + "3. Click Spin button<br><br>"
                + "<b>Validations</b><br>"
                + "     - Game is loaded<br>"
                + "     - Game UI loads correctly<br>"
                + "     - Balance is updated after win<br><br>"
                + "Stake:    " + constants.getStake()          + "<br><br>"
                + "<b>Before Spin Values</b><br>"
                + "Balance:  " + constants.getBalanceBefore()  + "<br>"
                + "Last Win: " + constants.getLastWinBefore()  + "<br><br>"
                + "<b>After Spin Values</b><br>"
                + "Balance:  " + constants.getBalanceAfter()  + "<br>"
                + "Last Win: " + constants.getLastWinAfter()  + "<br><br>"
                + "</pre>";

        Assert.assertEquals(stakeAfterRefresh, constants.getStake());
        Assert.assertEquals(constants.getBalanceAfter().replace("$", "").replace(",", ""), balanceAfterRefresh.replace("$", "").replace(",", ""));
        Assert.assertEquals(constants.getLastWinAfter(), lastWinAfterRefresh);
    }
}