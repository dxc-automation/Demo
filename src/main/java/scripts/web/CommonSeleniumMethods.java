package scripts.web;

import config.drivers.SeleniumDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonSeleniumMethods {

    public static boolean isElementClickable(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(SeleniumDriverManager.getSeleniumDriver(), Duration.ofSeconds(timeout));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean isElementRefreshed(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(SeleniumDriverManager.getSeleniumDriver(), Duration.ofSeconds(timeout));
        try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
