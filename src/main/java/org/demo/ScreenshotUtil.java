package org.demo;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("dd_HH_mm_ss").format(new Date());
        String fileName = "test-output/screenshots/" + testName + "_" + timestamp + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File(fileName);
        destination.getParentFile().mkdirs(); // Ensure the folder exists
        try {
            Files.copy(screenshot.toPath(), destination.toPath());
            System.out.println("📸 Screenshot saved: " + fileName);
            return destination.toString();
        } catch (IOException e) {
            System.err.println("❌ Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }

    public static String TakeScreenshot(WebDriver driver, String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String destDir = System.getProperty("user.dir") + "/test-output/screenshots/";
            Files.createDirectories(Paths.get(destDir));
            String destPath = destDir + name + "_" + System.currentTimeMillis() + ".png";
            Path dest = Paths.get(destPath);
            Files.copy(src.toPath(), dest);
            return destPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
