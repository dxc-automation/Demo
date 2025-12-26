package pages.web.plus500;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;
import java.text.ParseException;

import static utils.Calculations.convertDollarsToDouble;
import static utils.Excel.*;

public class BuySellPage extends BasePage {


    private final By sidebar = By.id("sidebar");
    private final By sidebarBuy  = By.xpath("//div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/button[1]/span[2]");
    private final By sidebarSell = By.xpath("//div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/button[1]/span[2]");
    private final By sidebarPercent = By.xpath("//span[@class='percent']");
    private final By sidebarCloseAtProfitSwitchBtn = By.id("close-at-profit-checkbox");
    private final By sidebarCloseAtProfitInput = By.id("close-at-profit-rate");
    private final By sidebarCloseAtProfitPlusBtn = By.xpath("//div[@class='spinbox check enabled']//button[@class='icon-plus ui-spinner-button ui-spinner-up']");
    private final By sidebarExpectedProfit = By.id("profitAmount");
    private final By sidebarExpectedProfitPercent = By.id("profitPercent");
    private final By sidebarExpectedLoss = By.id("lossAmount");
    private final By sidebarExpectedLossPercent = By.id("lossPercent");
    private final By sidebarPlaceOrder      = By.id("trade-button");
    private final By sidebarPlus = By.xpath("//div[@id='amountInputWrap']//button[@class='icon-plus ui-spinner-button ui-spinner-up']");
    private final By sidebarMinus = By.xpath("//div[@id='amountInputWrap']//button[@class='icon-minus ui-spinner-button ui-spinner-down']");
    private final By sidebarFunds = By.cssSelector("div[class='sidebar-alert success icon-check'] strong");

    Actions actions = new Actions(driver);

    public BuySellPage(WebDriver driver) {
        super(driver);
    }


    //  ACTIONS

    public double getBuyPrice() throws ParseException {
        wait.until(ExpectedConditions.elementToBeClickable(sidebarBuy));
        String value = driver.findElement(sidebarBuy).getText();
        System.out.println("Buy price: " + value);
        return convertDollarsToDouble("$" + value);
    }


    public double getSellPrice() throws ParseException {
        wait.until(ExpectedConditions.elementToBeClickable(sidebarSell));
        String value = driver.findElement(sidebarSell).getText();
        System.out.println("Sell price: " + value);
        return convertDollarsToDouble("$" + value);
    }


    public String getPercents() throws ParseException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(sidebarPercent));
        String value = driver.findElement(sidebarPercent).getText().replace("%", "");
        System.out.println("Percents: " + value);
        return value;
    }


    public void clickSidebarBuyBtn() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(sidebarBuy));
        driver.findElement(sidebarBuy).click();
        Thread.sleep(longSleep);
    }


    public BigDecimal setCloseAtProfit() throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(sidebarCloseAtProfitSwitchBtn));
        driver.findElement(sidebarCloseAtProfitSwitchBtn).click();
        Thread.sleep(longSleep);

        String averageHigh = String.valueOf(tradingConstants.getHighAverage());
        wait.until(ExpectedConditions.elementToBeClickable(sidebarCloseAtProfitInput));
        driver.findElement(sidebarCloseAtProfitInput).click();
        Thread.sleep(shortSleep);
        driver.findElement(sidebarCloseAtProfitInput).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        Thread.sleep(shortSleep);
        driver.findElement(sidebarCloseAtProfitInput).sendKeys(averageHigh);
        Thread.sleep(longSleep);
        driver.findElement(sidebarCloseAtProfitPlusBtn).click();
        Thread.sleep(longSleep);

        Object expectedProfit;
        BigDecimal profit;

        expectedProfit = driver.findElement(sidebarExpectedProfit).getText().replace("лв.", "");
        writeToExcel("Constants", 0, 0, expectedProfit);

        profit = convertDoubleToBigDecimal(readFromExcel("Profit", 0, 0));

        while(profit.doubleValue() <= 100.000) {
            expectedProfit = driver.findElement(sidebarExpectedProfit).getText().replace("лв.", "");
            System.out.println("\nExpected Profit: " + expectedProfit);
            writeToExcel("Constants", 0, 0, expectedProfit);

            profit = convertDoubleToBigDecimal(readFromExcel("Profit", 0, 0));
            System.out.println("\nReading.....\nExpected Profit: " + profit);

            BigDecimal addCloseAtProfit = new BigDecimal(100.00);
            BigDecimal newCloseAtProfit = profit.add(addCloseAtProfit);

            driver.findElement(sidebarCloseAtProfitInput).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            Thread.sleep(shortSleep);
            driver.findElement(sidebarCloseAtProfitInput).sendKeys(newCloseAtProfit.toString());
            Thread.sleep(longSleep);
            driver.findElement(sidebarCloseAtProfitPlusBtn).click();
            Thread.sleep(longSleep);

            expectedProfit = driver.findElement(sidebarExpectedProfit).getText().replace("лв.", "");
            System.out.println("\nNew Expected Profit: " + expectedProfit);

            writeToExcel("Constants", 0, 0, expectedProfit);
            profit = convertDoubleToBigDecimal(readFromExcel("Profit", 0, 0));

            if (profit.doubleValue() <= 100.00) {
                wait.until(ExpectedConditions.elementToBeClickable(sidebarCloseAtProfitInput));
                driver.findElement(sidebarCloseAtProfitInput).click();
                driver.findElement(sidebarCloseAtProfitInput).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                Thread.sleep(shortSleep);
                driver.findElement(sidebarCloseAtProfitInput).sendKeys(tradingConstants.getHighMax().toString());
                Thread.sleep(longSleep);
                driver.findElement(sidebarCloseAtProfitPlusBtn).click();
                Thread.sleep(longSleep);
            }
        }
        return profit;
    }

}