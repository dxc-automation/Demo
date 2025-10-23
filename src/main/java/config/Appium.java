package config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Appium {

    //Instantiate Appium Driver
    static AppiumDriver appium = null;
    static String deviceName = "Google Nexus 4";

    private static void runEmulator() throws IOException, InterruptedException {
        DefaultExecutor executor = new DefaultExecutor();
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        CommandLine launchEmul = new CommandLine("C:/Program Files/Genymobile/Genymotion/player");
        launchEmul.addArgument("--vm-name");
        launchEmul.addArgument("\"" + deviceName + "\"");
        executor.setExitValue(1);
        executor.execute(launchEmul, resultHandler);
        Thread.sleep(180000);
    }

    public static AppiumDriver initAppium() throws IOException, InterruptedException {
        runEmulator();

        DesiredCapabilities options = new DesiredCapabilities();
        options.setCapability("appium:udid", "192.168.14.104:5555");
        options.setCapability("appium:platformName", "Android");
        options.setCapability("appium:automationName", "UiAutomator2");
        options.setCapability("appium:deviceName", deviceName);
        options.setCapability("appium:noReset", true);
        options.setCapability("appium:appPackage", "com.android.chrome");
        options.setCapability("appium:appActivity", "com.google.android.apps.chrome.Main");
        options.setCapability("appium:newCommandTimeout", 3600);
        options.setCapability("appium:connectHardwareKeyboard", true);

        //Instantiating Android Driver and using Appium server host and port
        return appium = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    }

    public static void startServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"true\"\"}\"\"");
            Thread.sleep(10000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stopServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("taskkill /F /IM node.exe");
            runtime.exec("taskkill /F /IM cmd.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
