package pages.mobile;

import config.drivers.AndroidDriverManager;
import config.drivers.SeleniumDriverManager;
import data.Endpoints;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.time.Duration;

public class IrishWildMobilePage extends PageObjects {

    private WebDriverWait wait;
    private JavascriptExecutor js;

    public IrishWildMobilePage(AndroidDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    @AndroidFindBy(xpath = "//android.view.View[@resource-id='root']/android.view.View[1]/android.view.View[1]/android.view.View/android.widget.TextView[2]")
    private WebElement balanceText;

    @AndroidFindBy(xpath = "//android.view.View[@resource-id='root']/android.view.View[1]/android.view.View[2]/android.widget.Button[3]")
    private WebElement spinButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@text=\"AUTO\"]")
    private WebElement autoButton;

    @AndroidFindBy(xpath = "///android.view.View[@resource-id='root']/android.view.View[1]/android.view.View[2]/android.widget.Button[2]")
    private WebElement playButton;

    @AndroidFindBy(xpath = "//android.view.View[@resource-id=\"root\"]/android.view.View[1]/android.view.View[2]/android.widget.TextView[2]")
    private WebElement lastWinText;

    @AndroidFindBy(xpath = "//android.widget.TextView[4]")
    private WebElement stakeText;

    @AndroidFindBy(xpath = "//android.view.View[@resource-id='root']/android.view.View[1]/android.view.View[2]/android.widget.Button[2]")
    private WebElement stakeIncreaseButton;

    @AndroidFindBy(xpath = "//android.view.View[@resource-id='root']/android.view.View[1]/android.view.View[2]/android.widget.Button[1]")
    private WebElement stakeDecreaseButton;



    public void openIrishWildGamePage() {
        driver.get(Endpoints.getIrishWildsURL());
        wait.until(ExpectedConditions.elementToBeClickable(playButton));
    }


    public void refreshGamePage() {
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.elementToBeClickable(playButton));
    }


    public void clickPlayGame() {
        wait.until(ExpectedConditions.elementToBeClickable(playButton));
        playButton.click();
        wait.until(ExpectedConditions.visibilityOf(balanceText));
    }


    public String getBalance() {
        String balance = "";
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        return balance = balanceText.getText().replace(",", "");
    }


    public String getLastWinAmount() {
        String lastWinAmount = "";
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        return lastWinAmount = lastWinText.getText().replace(",", "");
    }


    public String getStakeAmount() {
        String stake = "";
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        return stake = stakeText.getText();
    }


    public void clickSpinButton() {
        wait.until(ExpectedConditions.elementToBeClickable(spinButton));
        spinButton.click();
    }


    public void clickIncreaseStake() {
        wait.until(ExpectedConditions.elementToBeClickable(stakeIncreaseButton));
        stakeIncreaseButton.click();
    }


    public void clickDecreaseStake() {
        wait.until(ExpectedConditions.elementToBeClickable(stakeDecreaseButton));
        stakeDecreaseButton.click();
    }


    public int spinUntilWin() {
        WebDriverWait wait = new WebDriverWait(SeleniumDriverManager.getSeleniumDriver(), Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        int counter = 0;

        while (true) {
            counter++;
            wait.until(ExpectedConditions.elementToBeClickable(autoButton));
            spinButton.click();
            wait.until(ExpectedConditions.elementToBeClickable(autoButton));
            String amount = getLastWinAmount();
            System.out.println(amount);
            if (!amount.equalsIgnoreCase("$0.00")) {
                break;
            }
        }
        return counter;
    }
}