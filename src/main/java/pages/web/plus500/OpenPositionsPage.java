package pages.web.plus500;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.Duration;

import static utils.Calculations.convertDollarsToDouble;
import static utils.Excel.readFromExcel;

public class OpenPositionsPage extends BasePage {

    Actions actions = new Actions(driver);


    //  LOCATORS
    private final By firstRow  = By.xpath(" //div[@class='open-positions']/div[@class='section-table']/div[@class='section-table-body']/div[1]");
    private final By secondRow = By.xpath(" //div[@class='open-positions']/div[@class='section-table']/div[@class='section-table-body']/div[2]");

    private final By firstRowClosePositionBtn = By.xpath("//button[@class='buySellButton icon-times']");

    private final By sidebarProfit = By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/span[2]");
    private final By sidebarClosePositionBtn = By.xpath("//button[normalize-space()='Close Position']");

    private final By nameFirstRowTxt             = By.xpath("//strong[@class='name']");
    private final By profitLossFirstRowTxt       = By.xpath("//div[@id='openPositionsRepeater']//div[@class='net-pl'][1]");
    private final By currentValueFirstRowTxt     = By.xpath("//div[@id='openPositionsRepeater']//div[@class='value'][1]");
    private final By changeFirstRowTxt           = By.xpath("//div[@id='openPositionsRepeater']//div[@class='change'][1]");
    private final By adjustmentsFirstRowTxt      = By.xpath("//div[@id='openPositionsRepeater']//div[@class='adjustments'][1]");
    private final By overnightFundingFirstRowTxt = By.xpath("//div[@id='openPositionsRepeater']//div[@class='premium'][1]");
    private final By net                         = By.className("net-pl");

    public OpenPositionsPage(WebDriver driver) {
        super(driver);
    }


    //  ACTIONS

    public void clickOpenPositions() {
        wait.until(ExpectedConditions.elementToBeClickable(HomePage.openPositionsNavBtn));
        driver.findElement(HomePage.openPositionsNavBtn).click();
    }


    private void convert(String value) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        // parse the string value
        BigDecimal parsedStringValue = (BigDecimal) decimalFormat.parse(value);
        System.out.println("Big Decimal " + parsedStringValue);
    }


    public void getFirstRowDetails() throws ParseException, IOException {
        clickOpenPositions();
        wait.until(ExpectedConditions.presenceOfElementLocated(nameFirstRowTxt));

        String name = driver.findElement(nameFirstRowTxt).getText();
        tradingConstants.setNameFirstRow(name);

        WebElement element = driver.findElement(firstRow);
        actions.doubleClick(element).build().perform();

        wait.until(ExpectedConditions.elementToBeClickable(sidebarProfit));
        String profit = driver.findElement(sidebarProfit).getText().replace("лв.", "");
        System.out.println(profit);

        try {
            convertDollarsToDouble(profit.replace(".",","));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String value = profit.substring(0, profit.indexOf("."));
        System.out.println("\nBGN " + value);

        Object a = readFromExcel("Properties", 0, 0);
    }
}

