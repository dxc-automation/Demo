package org.demo;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public static String screenshot(WebDriver driver, String name) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String fileName = "test-output/screenshots/" + name + ".png";
        File destination = new File(fileName);
        destination.getParentFile().mkdirs(); // create dirs if not exists
        if (fileName != null) {
            try {
                Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("🔻 Screenshot saved at: " + fileName);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return fileName;
    }

    public static String getScreenshot(WebDriver driver)
    {
        TakesScreenshot ts=(TakesScreenshot) driver;

        File src=ts.getScreenshotAs(OutputType.FILE);

        String path=System.getProperty("user.dir")+"/test-output/screenshots/" + System.currentTimeMillis() + ".png";

        File destination=new File(path);

        try
        {
            FileUtils.copyFile(src, destination);
        } catch (IOException e)
        {
            System.out.println("Capture Failed "+e.getMessage());
        }

        return path;
    }
}
