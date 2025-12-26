package pages.web.plus500;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;


public class LoginPage extends BasePage {

    private final By permissionsPrompt = By.id("pre-permission-prompt-placeholder");
    private final By permissionsPromptAllowBtn = By.xpath("//div[@id='pre-permission-prompt-placeholder']//div[@class='pre-push-footer']/button[1]");

    private final By searchInput = By.id("main-search-input");
    private final By searchDropdownResult1 = By.xpath("//div[@class='search']/ul[@class='ui-menu ui-widget ui-widget-content ui-autocomplete ui-front']/li[1]/div[1]");

    private final By openPositionsNavBtn = By.id("openPositionsNav");
    private final By reCaptchaCheckbox = By.id("recaptcha-anchor");

    private final By googleEmailInput = By.id("identifierId");
    private final By googlePasswordInput = By.xpath("//input[@name='Passwd']");
    private final By googlePasswordNextBtn = By.xpath("//div[@id='passwordNext']//button[@type='button']");
    private final By googleEmailNextBtn = By.xpath("//div[@id='identifierNext']//button[@type='button']");
    private final By googleBtn = By.xpath("//button[@class='google-api-login']");
    private final By alreadyHaveAccount = By.id("newUserCancelExperiment");
    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    private final By submitLoginBtn = By.id("submitLogin");
    private final By demoBtn = By.id("demoMode");
    private final By realBtn = By.id("realMoney");


    Actions actions = new Actions(driver);

    public LoginPage(WebDriver driver) {
        super(driver);
    }


    //  ACTIONS

    private void openWeb() throws IOException {
        driver.get("https://app.plus500.com/trade");

        LogEntries entry = driver.manage().logs().get(LogType.BROWSER);
        String log = System.getProperty("user.dir") +"/test-output/log.txt";

        // Retrieving all logs
        List<LogEntry> logs = entry.getAll();
        BufferedWriter writer = new BufferedWriter(new FileWriter(log, true));

        // Printing details separately
        for (LogEntry e : logs) {
            writer.write(e.toJson().toString());
        }
    }


    private void allowPermissions() throws InterruptedException {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(permissionsPrompt));
            if (isElementVisible(permissionsPrompt)) {
                driver.findElement(permissionsPromptAllowBtn).click();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loginGoogle(String email, String password) throws InterruptedException, IOException {
        openWeb();

        wait.until(ExpectedConditions.elementToBeClickable(realBtn));
        driver.findElement(realBtn).click();

        wait.until(ExpectedConditions.elementToBeClickable(alreadyHaveAccount));
        driver.findElement(alreadyHaveAccount).click();

        wait.until(ExpectedConditions.elementToBeClickable(googleBtn));
        driver.findElement(googleBtn).click();
        Thread.sleep(1500);

        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);

        wait.until(ExpectedConditions.elementToBeClickable(googleEmailInput));
        driver.findElement(googleEmailInput).click();
        driver.findElement(googleEmailInput).sendKeys(email);
        driver.findElement(googleEmailNextBtn).click();

        wait.until(ExpectedConditions.elementToBeClickable(googlePasswordInput));
        driver.findElement(googlePasswordInput).click();
        driver.findElement(googlePasswordInput).sendKeys(password);
        driver.findElement(googlePasswordNextBtn).click();
    }


    public void loginReal(String email, String password) throws InterruptedException, IOException {
        openWeb();

        wait.until(ExpectedConditions.elementToBeClickable(realBtn));
        driver.findElement(realBtn).click();

        wait.until(ExpectedConditions.elementToBeClickable(alreadyHaveAccount));
        driver.findElement(alreadyHaveAccount).click();

        wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        driver.findElement(emailInput).click();
        driver.findElement(emailInput).sendKeys(email);

        wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        driver.findElement(passwordInput).click();
        driver.findElement(passwordInput).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(submitLoginBtn));
        driver.findElement(submitLoginBtn).click();

        allowPermissions();
    }


    public void loginDemo(String email, String password) throws InterruptedException, IOException {
        openWeb();

        wait.until(ExpectedConditions.elementToBeClickable(demoBtn));
        driver.findElement(demoBtn).click();

        wait.until(ExpectedConditions.elementToBeClickable(alreadyHaveAccount));
        driver.findElement(alreadyHaveAccount).click();

        wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        driver.findElement(emailInput).click();
        driver.findElement(emailInput).sendKeys(email);

        wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        driver.findElement(passwordInput).click();
        driver.findElement(passwordInput).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(submitLoginBtn));
        WebElement element = driver.findElement(submitLoginBtn);
        actions.click(element).build().perform();

        allowPermissions();
    }
}