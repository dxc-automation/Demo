package config;

import data.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.demo.ScreenshotUtil;
import org.demo.Utilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import static config.TestListener.extent;


@Slf4j
public abstract class BaseTest {

    public  static final  String  root = System.getProperty("user.dir");
    public  static final  String  path = root + File.separator + "library-manager" + File.separator + "LibraryManager.bat";
    public  static Constants constants;
    public  static WebDriver driver;
    private static Desktop   desktop;

    public static String testName;


    public static void setDriver(WebDriver driverInstance) {
        driver = driverInstance;
    }


    @BeforeSuite
    public void setupReport() throws IOException, ParseException, InterruptedException {
        System.setProperty("java.awt.headless", "false");
        desktop = Desktop.getDesktop();
        desktop.open(new File(path));

        constants = new Constants();
        constants.readTestData("TestData", 1);
    }


    @BeforeMethod
    public void getTestName(Method method) {
        testName = method.getName();
        Test testAnnotation = method.getAnnotation(Test.class);
        if (testAnnotation != null && !testAnnotation.testName().isEmpty()) {
            testName = testAnnotation.testName();
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
