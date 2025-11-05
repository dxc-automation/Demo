package config.drivers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

@Slf4j
public class SeleniumDriverManager {

    // command used to get list of running task
    private static final String TASKLIST = "tasklist";

    // command used to kill a task
    private static final String KILL = "taskkill /IM ";

    private static final ThreadLocal<WebDriver> seleniumDriver = new ThreadLocal<>();

    //  SELENIUM
    public static void setupSeleniumDriver(String browser) throws InterruptedException {
        try {
            if (browser.toLowerCase().equals("chrome")) {
                WebDriverManager.chromedriver().setup();
                //  System.setProperty("webdriver.chrome.driver", chromedriver);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");
                //options.addArguments("--headless=new");
                options.setCapability("goog:loggingPrefs", Map.of("performance", "ALL"));

                seleniumDriver.set(new ChromeDriver(options));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static WebDriver getSeleniumDriver() {
        return seleniumDriver.get();
    }

    public static void closeSeleniumDriver() {
        if (seleniumDriver.get() != null) {
            seleniumDriver.get().close();
        }
    }

    public static void quitSeleniumDriver() {
        if (seleniumDriver.get() != null) {
            seleniumDriver.get().quit();
            seleniumDriver.remove();
        }
    }
}