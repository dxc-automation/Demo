package pages.web;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private final By allowCookiesBtn = By.xpath("//div[@role='dialog']//button[@class='_a9-- _ap36 _asz1']");
    private final By loginBtn = By.xpath("/html[1]/body[1]/div[5]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]");
    private final By usernameInput = By.xpath("//input[@name='username']");
    private final By passwordInput = By.xpath("//input[@name='password']");
    private final By submitBtn = By.xpath("//button[@type='submit']");
    private final By notNowBtn = By.xpath("(//div[contains(text(),'Not now')])[1]");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }


    public void openLoginPage(String url) {
        driver.get(url);
    }

    public void allowCookies() {
        wait.until(ExpectedConditions.presenceOfElementLocated(allowCookiesBtn));
        driver.findElement(allowCookiesBtn).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(loginBtn));
        driver.findElement(loginBtn).click();
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.elementToBeClickable(usernameInput));
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
        driver.findElement(submitBtn).click();
    }
}