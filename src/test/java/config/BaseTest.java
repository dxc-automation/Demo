package config;

import data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.demo.ScreenshotUtil;
import org.demo.Utilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static config.ExtentTestNGListener.testCategory;


@Slf4j
public class BaseTest {

    private static String chromedriver = "src/main/resources/drivers/chromedriver_141/chromedriver.exe";
    public  static final  String  root = System.getProperty("user.dir");
    public  static final  String  path = root + File.separator + "library-manager" + File.separator + "LibraryManager.bat";

    public static WebDriver driver;
    public static Desktop   desktop;

    public static String testName = "";

    public static String udid;


    public static WebDriver setupDriver(String browser) throws InterruptedException {
        try {
            if (browser.toLowerCase().equals("chrome")) {
                WebDriverManager.chromedriver().setup();
              //  System.setProperty("webdriver.chrome.driver", chromedriver);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");
                options.addArguments("--headless=new");
                options.setCapability("goog:loggingPrefs", Map.of("performance", "ALL"));

                driver = new ChromeDriver(options);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Thread.sleep(3000);
        return driver;
    }


    @BeforeSuite
    public void readDevice() throws InterruptedException {
        udid = System.getProperty("env");
        System.out.println("UDID: " + udid);

        driver = setupDriver("chrome");
    }


    @AfterSuite(alwaysRun = true)
    public void tearDownReport() throws IOException {
        if (driver != null) {
            driver.quit();
        }
        try {
            Files.createDirectories(Paths.get("test-output-archive"));
        } catch (Exception exception) {
            System.out.println("test-output-archive directory already exists");
        }
        String folder = "test-output";
        String file   = "test-output-archive/" + Utilities.getDate() + ".zip";
        Utilities.zip(folder, file);

        FileUtils.forceMkdir(new File(folder));

        try {
            new ProcessBuilder("cmd", "/c", "taskkill /IM LibraryManager.bat /F").start();
        } catch (IOException e) {
        }
    }
}
