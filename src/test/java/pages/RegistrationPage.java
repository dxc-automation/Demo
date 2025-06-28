package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class RegistrationPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private final By firstNameInput = By.id("firstName");
    private final By lastNameInput  = By.id("lastName");
    private final By emailInput     = By.id("userEmail");
    private final By mobileNumberInput = By.id("userNumber");
    private final By dateOfBirthInput  = By.id("dateOfBirthInput");
    private final By modalCloseButton  = By.id("closeLargeModal");
    private final By modalDialog       = By.className("modal-content");
    private final By submitButton      = By.id("submit");


    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }


    public void openFormPage() {
        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
        js.executeScript("document.getElementById('fixedban').style.display='none'");
        js.executeScript("document.querySelector('footer').style.display='none'");
    }


    public void enterFirstName(String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }


    public void enterLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }


    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }


    public void selectGender(String gender) {
        driver.findElement(By.xpath("//label[text()='" + gender + "']")).click();
    }


    public void enterMobileNumber(String number) {
        driver.findElement(mobileNumberInput).sendKeys(number);
    }


    public void selectDateOfBirth(String birthDate) throws ParseException {
        driver.findElement(dateOfBirthInput).click();

        WebElement element = driver.findElement(dateOfBirthInput);
        js.executeScript("arguments[0].value = '';", element);

        driver.findElement(dateOfBirthInput).sendKeys(birthDate);
        driver.findElement(mobileNumberInput).click();
    }


    public void enterSubject(String subject) {
        WebElement subjectInput = driver.findElement(By.id("subjectsInput"));
        subjectInput.sendKeys(subject);
        subjectInput.sendKeys(Keys.ENTER);
    }


    public void selectHobby(String hobby) {
        driver.findElement(By.xpath("//label[text()='" + hobby + "']")).click();
    }


    public void enterAddress(String address) {
        driver.findElement(By.id("currentAddress")).sendKeys(address);
    }


    public void selectStateAndCity(String state, String city) {
        js.executeScript("window.scrollBy(0,250)");
        WebElement stateInput = driver.findElement(By.id("react-select-3-input"));
        stateInput.sendKeys(state);
        stateInput.sendKeys(Keys.ENTER);

        WebElement cityInput = driver.findElement(By.id("react-select-4-input"));
        cityInput.sendKeys(city);
        cityInput.sendKeys(Keys.ENTER);
    }


    public void submitForm() {
        driver.findElement(By.id("submit")).click();
    }


    public boolean isConfirmationModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(modalDialog)).isDisplayed();
    }


    public boolean modalContainsText(String text) {
        WebElement modal = driver.findElement(modalDialog);
        return modal.getText().contains(text);
    }


    public void closeModal() {
        wait.until(ExpectedConditions.elementToBeClickable(modalCloseButton));
        driver.findElement(modalCloseButton).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalDialog));
    }
}