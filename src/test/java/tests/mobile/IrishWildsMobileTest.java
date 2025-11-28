
package tests.mobile;

import config.BaseTest;
import config.drivers.AndroidDriverManager;
import config.ExtentTestNGListener;
import config.drivers.SeleniumDriverManager;
import data.Constants;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.mobile.IrishWildMobilePage;
import utils.Calculations;

import java.io.IOException;
import java.text.ParseException;

import static utils.Utilities.copyFile;


@Listeners(ExtentTestNGListener.class)
public class IrishWildsMobileTest extends BaseTest {

    private IrishWildMobilePage gamePage;
    private Constants constants;



    @BeforeClass
    public void init() throws InterruptedException, IOException, ParseException {
        AndroidDriverManager.startAppiumServer();
     //   AndroidDriverManager.androidEmulator("start");

        gamePage = new IrishWildMobilePage(AndroidDriverManager.getDriver());

        constants = new Constants();
        constants.readTestData("TestData", 1);
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        AndroidDriverManager.androidEmulator("stop");
        SeleniumDriverManager.quitSeleniumDriver();
        AndroidDriverManager.stopAppiumServer();

        copyFile("C:\\Users\\KamboJah\\AppData\\Local\\Genymobile\\genymotion.log", "test-output\\genymotion.log");
        copyFile("C:\\Users\\KamboJah\\AppData\\Local\\Genymobile\\Genymotion\\deployed\\Google Nexus 4\\genymotion-player.log", "test-output\\genymotion-player.log");
    }


    @Test(testName = "[MOBILE] Irish Wilds - Validate Balance Update After Spin", description = "MOBILE", priority = 0)
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


    @Test(testName = "[MOBILE] Irish Wilds - Validate Balance Update After Win", description = "MOBILE", priority = 1)
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


    @Test(testName = "[MOBILE] Irish Wilds - Validate Decreased Stake Change", description = "MOBILE", priority = 2)
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

    @Test(testName = "[MOBILE] Irish Wilds - Validate Increased Stake Change", description = "MOBILE", priority = 3)
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

    @Test(testName = "[MOBILE] Irish Wilds - Validate Amounts After Refresh", description = "MOBILE", priority = 4)
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
