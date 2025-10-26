package pages;

import config.BaseTest;
import data.Endpoints;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IrishWildPage {

    private WebDriver seleniumDriver;
    private WebDriverWait wait;
    private JavascriptExecutor js;


    private final By playButton = By.xpath("//button[@class='button button__slider-play button__rounded-xl _visible']");
    private final By amountText = By.xpath("(//span[@class='amount'])[1]");
    private final By lastWinText = By.xpath("//div[@class='display win display__wrapper align-center _display-bg-black']//span[@class='amount'][1]");
    private final By stakeText = By.xpath("//div[@class='display stake display__wrapper align-center']//span[@class='amount'][1]");
    private final By spinButton = By.xpath("//button[@class='button button__rounded-xl arrows-spin-button']");


    public IrishWildPage(WebDriver driver) {
        this.seleniumDriver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void openIrishWildGamePage() {
        seleniumDriver.get(Endpoints.irish_wilds);
        wait.until(ExpectedConditions.elementToBeClickable(playButton));
    }

    public void playGame() {
        wait.until(ExpectedConditions.elementToBeClickable(playButton));
        seleniumDriver.findElement(playButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(amountText));
    }
}