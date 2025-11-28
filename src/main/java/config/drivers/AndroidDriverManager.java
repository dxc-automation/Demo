package config.drivers;

import static config.BaseTest.constants;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;


public class AndroidDriverManager {

    private static AndroidDriver driver;
    private static AppiumDriverLocalService service;


    //  APPIUM
    public static void startAppiumService() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", true);

        AppiumServiceBuilder builder;
        builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(4723);
        builder.usingAnyFreePort();
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"debug");

        //Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }


    public static void stopAppiumService() {
        if (service != null) {
            service.stop();
        }
    }


    public static AndroidDriver getDriver() {
        if (driver == null) {
            try {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("appium:udid", constants.getDeviceUDID());
                capabilities.setCapability("appium:platformName", "Android");
                capabilities.setCapability("appium:platformVersion", "13.0");
                capabilities.setCapability("appium:automationName", "UiAutomator2");
                capabilities.setCapability("appium:deviceName", constants.getDeviceName());
                capabilities.setCapability("appium:noReset", true);
                capabilities.setCapability("appium:appPackage", "com.android.chrome");
                capabilities.setCapability("appium:appActivity", "com.google.android.apps.chrome.Main");
                capabilities.setCapability("appium:newCommandTimeout", 3600);
                capabilities.setCapability("appium:connectHardwareKeyboard", true);

                ChromeOptions options = new ChromeOptions();
                options.setAndroidDeviceSerialNumber(constants.getDeviceUDID());
                options.setPlatformName("Android");
                options.setBrowserVersion("142");

                URL url = new URL("http://127.0.0.1:4723/wd/hub");

                driver = new AndroidDriver(url, capabilities);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Appium server URL", e);
            }
        }
        return driver;
    }


    public static void androidEmulator(String action) throws IOException, InterruptedException {
        String file = System.getProperty("user.dir") + "/src/main/resources/" + action + "_emulator.bat";

        String[] cmd = {
                "cmd.exe", "/c",
                "start " + file
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

        Thread.sleep(10000);
    }


    public static void startGenymotionEmulator() throws IOException, InterruptedException {
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


    //  *** Used for local execution
    public static void startAppiumServer() throws InterruptedException, IOException {
        String[] cmd = {
                "cmd.exe", "/c",
                "appium", "-a", "127.0.0.1", "-p", "4723", "--session-override", "-pa", "/wd/hub"
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

        Thread.sleep(10000);
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


    //  *** Used for local execution
    public static void stopAppiumServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("taskkill /F /IM node.exe");
            runtime.exec("taskkill /F /IM cmd.exe");
            System.out.println("\nAppium driver successfully stopped");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}