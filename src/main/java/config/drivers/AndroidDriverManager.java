package config.drivers;

import static config.BaseTest.constants;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import config.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;


public class AndroidDriverManager {

    private static AppiumDriver driver;
    private static AppiumDriverLocalService service;

    //  APPIUM
    public static void startAppiumService() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", true);

        AppiumServiceBuilder builder;
        builder = new AppiumServiceBuilder();
        builder.withLogFile(new File("test-output/appium.txt"));
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(4723);
        builder.usingAnyFreePort();
        builder.withArgument(GeneralServerFlag.BASEPATH, "/wd/hub");
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

    public static AppiumDriver getDriver() {
        if (driver == null) {
            try {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("appium:udid", constants.getDeviceUDID());
                capabilities.setCapability("appium:platformName", "Windows");
                capabilities.setCapability("appium:automationName", "Chromium");
                capabilities.setCapability("appium:deviceName", constants.getDeviceName());
                capabilities.setCapability("appium:noReset", true);
                capabilities.setCapability("appium:browserName", "chrome");
                //capabilities.setCapability("appium:appPackage", "com.android.chrome");
                //capabilities.setCapability("appium:appActivity", "com.google.android.apps.chrome.Main");


                driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Appium server URL", e);
            }
        }
        return driver;
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
            System.out.println(e.getMessage());
        }
    }

    //  *** Used for local execution
    public static void startAppiumServer() throws InterruptedException, IOException {
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