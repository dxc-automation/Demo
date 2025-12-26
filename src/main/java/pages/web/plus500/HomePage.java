package pages.web.plus500;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }


    //  LOCATORS

    public static final By permissionsPrompt = By.id("pre-permission-prompt-placeholder");
    public static final By permissionsPromptAllowBtn = By.xpath("//div[@id='pre-permission-prompt-placeholder']//div[@class='pre-push-footer']/button[1]");

    public static final By searchInput = By.id("main-search-input");
    public static final By searchDropdownResult1 = By.xpath("//div[@class='search']/ul[@class='ui-menu ui-widget ui-widget-content ui-autocomplete ui-front']/li[1]/div[1]");

    public static final By openPositionsNavBtn = By.id("openPositionsNav");

    public static final By reCaptchaCheckbox = By.id("recaptcha-anchor");



    public void searchProduct(String product) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        driver.findElement(searchInput).click();
        driver.findElement(searchInput).sendKeys(product);
        Thread.sleep(longSleep);

        wait.until(ExpectedConditions.elementToBeClickable(searchDropdownResult1));
        driver.findElement(searchDropdownResult1).click();
        Thread.sleep(longSleep);
    }
}