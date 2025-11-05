package config;

import config.drivers.AndroidDriverManager;
import config.drivers.SeleniumDriverManager;
import data.Constants;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import utils.Utilities;
import org.testng.annotations.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class BaseTest {

    private static String chromedriver = "src/main/resources/drivers/chromedriver_141/chromedriver.exe";
    public  static final  String  root = System.getProperty("user.dir");
    public  static final  String  path = root + File.separator + "library-manager" + File.separator + "LibraryManager.bat";

    protected static AppiumDriver  appiumDriver;
    public static Desktop   desktop;
    public static Constants constants = new Constants();


    @BeforeSuite
    @Parameters({"deviceName", "deviceUDID"})
    public void readDevice(@Optional("Nexus_4") String deviceName, @Optional("192.168.127.101:5555") String deviceUDID) throws InterruptedException, IOException {
        String name = System.getProperty("name");
        if (name != null) {
            constants.setDeviceName(name);
            System.out.println("Detected server device name: " + constants.getDeviceName());
        } else {
            constants.setDeviceName(deviceName);
            System.out.println("Detected local device name: " + constants.getDeviceName());
        }

        String udid = System.getProperty("udid");
        if (udid != null) {
            constants.setDeviceUDID(udid);
            System.out.println("Detected server device udid: " + constants.getDeviceUDID());
        } else {
            constants.setDeviceUDID(deviceUDID);
            System.out.println("Detected local device udid: " + constants.getDeviceUDID());
        }
    }


    @AfterSuite(alwaysRun = true)
    public void tearDownReport() throws IOException {
        SeleniumDriverManager.quitSeleniumDriver();
        AndroidDriverManager.stopAndroidEmulator();
        AndroidDriverManager.stopAppiumService();

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
