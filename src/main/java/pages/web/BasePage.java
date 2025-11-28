package pages.web;

import data.TradingConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;


public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected int longSleep = 1500;
    protected TradingConstants tradingConstants = new TradingConstants();
    private  ArrayList<String> tabs;


    public BasePage(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }


    public boolean isElementPresent(By locatorKey) throws InterruptedException {
        try {
            Thread.sleep(3000);
            driver.findElement(locatorKey);
            System.out.println("Element prompt detected");
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("Element prompt not detected");
            return false;
        }
    }


    public void switchToNewTab() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");

        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }


    public void switchToPreviousTab() {
        driver.switchTo().window(tabs.get(0));
    }


    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    public void closeCurrentTab() {
        driver.close();
    }
}