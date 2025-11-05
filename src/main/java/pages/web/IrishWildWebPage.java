package pages.web;

import config.drivers.SeleniumDriverManager;
import data.Endpoints;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IrishWildWebPage {

    private WebDriver seleniumDriver;
    private WebDriverWait wait;
    private JavascriptExecutor js;


    private final By autoButton   = By.xpath("//button[@class='button']");
    private final By playButton   = By.xpath("//button[@class='button button__slider-play button__rounded-xl _visible']");
    private final By balanceText  = By.xpath("(//span[@class='amount'])[1]");
    private final By lastWinText  = By.xpath("//div[@class='display win display__wrapper align-center _display-bg-black']//span[@class='amount'][1]");

    private final By stakeText           = By.xpath("//div[@class='display stake display__wrapper align-center']//span[@class='amount'][1]");
    private final By stakeIncreaseButton = By.xpath("//div[@class='quantity quantity__wrapper false']//button[2]");
    private final By stakeDecreaseButton = By.xpath("//div[@class='quantity quantity__wrapper false']//button[1]");

    private final By spinButton   = By.xpath("//button[@class='button button__rounded-xl arrows-spin-button']");


    public IrishWildWebPage(WebDriver driver) {
        this.seleniumDriver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
    }


    public void openIrishWildGamePage() {
        seleniumDriver.get(Endpoints.getIrishWildsURL());
        wait.until(ExpectedConditions.elementToBeClickable(playButton));
    }


    public void refreshGamePage() {
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        seleniumDriver.navigate().refresh();
        wait.until(ExpectedConditions.elementToBeClickable(playButton));
    }


    public void clickPlayGame() {
        wait.until(ExpectedConditions.elementToBeClickable(playButton));
        seleniumDriver.findElement(playButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(balanceText));
    }


    public String getBalance() {
        String balance = "";
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        return balance = seleniumDriver.findElement(balanceText).getText().replace(",", "");
    }


    public String getLastWinAmount() {
        String lastWinAmount = "";
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        return lastWinAmount = seleniumDriver.findElement(lastWinText).getText().replace(",", "");
    }


    public String getStakeAmount() {
        String stake = "";
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        return stake = seleniumDriver.findElement(stakeText).getText();
    }


    public void clickSpinButton() {
        wait.until(ExpectedConditions.elementToBeClickable(spinButton));
        seleniumDriver.findElement(spinButton).click();
    }


    public void clickIncreaseStake() {
        wait.until(ExpectedConditions.elementToBeClickable(stakeIncreaseButton));
        seleniumDriver.findElement(stakeIncreaseButton).click();
    }


    public void clickDecreaseStake() {
        wait.until(ExpectedConditions.elementToBeClickable(stakeDecreaseButton));
        seleniumDriver.findElement(stakeDecreaseButton).click();
    }


    public int spinUntilWin() {
        WebDriverWait wait = new WebDriverWait(SeleniumDriverManager.getSeleniumDriver(), Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(autoButton));
        int counter = 0;

        while (true) {
            counter++;
            wait.until(ExpectedConditions.elementToBeClickable(autoButton));
            seleniumDriver.findElement(spinButton).click();
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