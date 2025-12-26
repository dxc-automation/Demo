package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.Map;

@Slf4j
public class SeleniumDriverManager {

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
                ChromeDriverService service = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File("src/main/resources/drivers/chromedriver_142/chromedriver.exe"))
                        .usingAnyFreePort()
                        .build();
                service.start();
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