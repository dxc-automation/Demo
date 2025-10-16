package config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class Appium {

    //Instantiate Appium Driver
    AppiumDriver appium = null;

    public AppiumDriver initAppium() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Galaxy A23 5G");
        capabilities.setCapability("platformVersion", "14");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("udid", "R5CT80S9RPW");
        capabilities.setCapability("uiautomator2ServerLaunchTimeout", "90000");

        //setting capability for application we want to test
        capabilities.setCapability("appPackage", "com.android.chrome");
        capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");

        //Instantiating Android Driver and using Appium server host and port
        return appium = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
}
