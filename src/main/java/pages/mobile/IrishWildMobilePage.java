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
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IrishWildMobilePage {

    private AppiumDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;


    private final By balanceText = By.xpath("//android.view.View[@resource-id=\"root\"]/android.view.View[1]/android.view.View[1]/android.view.View/android.widget.TextView[2]");
    private final By spinButton = By.xpath("//android.view.View[@resource-id='root']/android.view.View[1]/android.view.View[2]/android.widget.Button[3]");

    private final By autoButton   = By.xpath("//android.widget.Button[@text=\"AUTO\"]");
    private final By playButton   = By.xpath("///android.view.View[@resource-id='root']/android.view.View[1]/android.view.View[2]/android.widget.Button[2]");
    private final By lastWinText  = By.xpath("//android.view.View[@resource-id=\"root\"]/android.view.View[1]/android.view.View[2]/android.widget.TextView[2]");

    private final By stakeText           = By.xpath("//android.widget.TextView[4]");
    private final By stakeIncreaseButton = By.xpath("//android.view.View[@resource-id='root']/android.view.View[1]/android.view.View[2]/android.widget.Button[2]");
    private final By stakeDecreaseButton = By.xpath("//android.view.View[@resource-id='root']/android.view.View[1]/android.view.View[2]/android.widget.Button[1]");


    public IrishWildMobilePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(180));
        this.js = (JavascriptExecutor) driver;
    }


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
        driver.findElement(playButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(balanceText));
    }


    public String getBalance() {
        String balance = "";
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        return balance = driver.findElement(balanceText).getText().replace(",", "");
    }


    public String getLastWinAmount() {
        String lastWinAmount = "";
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        return lastWinAmount = driver.findElement(lastWinText).getText().replace(",", "");
    }


    public String getStakeAmount() {
        String stake = "";
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        return stake = driver.findElement(stakeText).getText();
    }


    public void clickSpinButton() {
        wait.until(ExpectedConditions.elementToBeClickable(spinButton));
        driver.findElement(spinButton).click();
    }


    public void clickIncreaseStake() {
        wait.until(ExpectedConditions.elementToBeClickable(stakeIncreaseButton));
        driver.findElement(stakeIncreaseButton).click();
    }


    public void clickDecreaseStake() {
        wait.until(ExpectedConditions.elementToBeClickable(stakeDecreaseButton));
        driver.findElement(stakeDecreaseButton).click();
    }


    public int spinUntilWin() {
        WebDriverWait wait = new WebDriverWait(SeleniumDriverManager.getSeleniumDriver(), Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        int counter = 0;

        while (true) {
            counter++;
            wait.until(ExpectedConditions.elementToBeClickable(autoButton));
            driver.findElement(spinButton).click();
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