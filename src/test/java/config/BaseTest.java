package config;

import browser.ChromeDriverBuilder;
import data.Constants;
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
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import com.valensas.undetected.chrome.driver.*;


@Slf4j
public class BaseTest {

    private static String chromedriver = "src/main/resources/drivers/chromedriver.exe";
    public  static final  String  root = System.getProperty("user.dir");
    public  static final  String  path = root + File.separator + "library-manager" + File.separator + "LibraryManager.bat";

    public  static Constants constants;
    public  static WebDriver driver;
    private static Desktop   desktop;

    public static String testName;


    public static WebDriver setupDriver(String browser) throws InterruptedException {
        switch (browser.toLowerCase()) {

            case "firefox":
                driver = new FirefoxDriver();
                break;

            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", chromedriver);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");
                options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.7049.84 Safari/537.36");

                driver = new ChromeDriverBuilder().build(options, chromedriver);
                break;
        }
        Thread.sleep(3000);
        return driver;
    }


    @BeforeSuite
    public void setupReport() throws IOException, ParseException, InterruptedException {
        System.setProperty("java.awt.headless", "false");
        desktop = Desktop.getDesktop();
        desktop.open(new File(path));

        constants = new Constants();
        constants.readTestData("TestData", 1);
        constants.readInstagramData(1);
    }


    @BeforeMethod
    public void getTestName(Method method) {
        testName = method.getName();
        Test testAnnotation = method.getAnnotation(Test.class);
        if (testAnnotation != null && !testAnnotation.testName().isEmpty()) {
            testName = testAnnotation.testName();
        }
    }


    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }



    @AfterSuite(alwaysRun = true)
    public void tearDownReport() throws IOException {
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
