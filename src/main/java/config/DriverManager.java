package config;

import data.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static config.BaseTest.constants;

@Slf4j
public class DriverManager {

    // command used to get list of running task
    private static final String TASKLIST = "tasklist";
    // command used to kill a task
    private static final String KILL = "taskkill /IM ";

    private static final ThreadLocal<WebDriver>    seleniumDriver = new ThreadLocal<>();
    private static final ThreadLocal<AppiumDriver> appiumDriver   = new ThreadLocal<>();
    static String deviceName = "Google Nexus 4";


    //  SELENIUM
    public static void setupSeleniumDriver(String browser) throws InterruptedException {
        try {
            if (browser.toLowerCase().equals("chrome")) {
                WebDriverManager.chromedriver().setup();
                //  System.setProperty("webdriver.chrome.driver", chromedriver);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");
                options.addArguments("--headless=new");
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

    public static void quitSeleniumDriver() {
        if (seleniumDriver.get() != null) {
            seleniumDriver.get().quit();
            seleniumDriver.remove();
        }
    }


    //  APPIUM
    public static void setupAppiumDriver() throws IOException, InterruptedException {
        DesiredCapabilities options = new DesiredCapabilities();
        options.setCapability("appium:udid", constants.getDeviceUDID());
        options.setCapability("appium:platformName", "Android");
        options.setCapability("appium:automationName", "UiAutomator2");
        options.setCapability("appium:deviceName", deviceName);
        options.setCapability("appium:noReset", true);
        options.setCapability("appium:appPackage", "com.android.chrome");
        options.setCapability("appium:appActivity", "com.google.android.apps.chrome.Main");
        options.setCapability("appium:newCommandTimeout", 3600);
        options.setCapability("appium:connectHardwareKeyboard", true);

        //Instantiating Android Driver and using Appium server host and port
        appiumDriver.set(new AndroidDriver(new URL("http://127.0.0.1:4723"), options));
    }

    public static void startAndroidEmulator() throws IOException, InterruptedException {
        DefaultExecutor executor = new DefaultExecutor();
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        CommandLine launchEmul = new CommandLine("C:/Program Files/Genymobile/Genymotion/player");
        launchEmul.addArgument("--vm-name");
        launchEmul.addArgument("\"" + constants.getDeviceName() + "\"");
        executor.setExitValue(1);
        executor.execute(launchEmul, resultHandler);
        Thread.sleep(120000);
        System.out.println("\nAndroid emulator successfully started");
    }

    public static void stopAndroidEmulator() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("taskkill /F /IM player.exe");
            System.out.println("\nAndroid emulator successfully stopped");
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    public static AppiumDriver getAppiumDriver() {
        return appiumDriver.get();
    }

    public static void startAppiumServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            String[] cmd = {
                    "cmd.exe", "/c",
                    "appium", "-a", "127.0.0.1", "-p", "4723", "--session-override"
            };

            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(false);
            Process proc = pb.start();
            System.out.println("\nAppium driver successfully started");

            ExecutorService ex = Executors.newFixedThreadPool(2);
            StringBuilder stdoutBuf = new StringBuilder();
            StringBuilder stderrBuf = new StringBuilder();

            ex.submit(() -> streamToBuilder(proc.getInputStream(), stdoutBuf, "OUT"));
            ex.submit(() -> streamToBuilder(proc.getErrorStream(), stderrBuf, "ERR"));

            Thread.sleep(20000);
        } catch (IOException | InterruptedException e) {
            log.error(String.valueOf(e));
        }
    }

    private static void streamToBuilder(InputStream is, StringBuilder target, String label) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                target.append(line).append(System.lineSeparator());
                System.out.println("[" + label + "] " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopAppiumServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("taskkill /F /IM node.exe");
            runtime.exec("taskkill /F /IM cmd.exe");
            System.out.println("\nAppium driver successfully stopped");
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }
    }
}
