package config;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.demo.ScreenshotUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static config.BaseTest.test;


public class TestListener implements ITestListener {


    private static WebDriver driver;

    public static void setDriver(WebDriver driverInstance) {
        driver = driverInstance;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (driver != null) {
            String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String fileName = "test-output/screenshots/" + result.getName() + "_failed.png";
            File destination = new File(fileName);
            destination.getParentFile().mkdirs(); // create dirs if not exists
            if (screenshotPath != null) {
                try {
                    Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("🔻 Screenshot for failed test saved at: " + screenshotPath);
                test.fail("<br><b>FAILED ON SCREEN</b><br>", MediaEntityBuilder.createScreenCaptureFromPath("../" + fileName).build());
                test.fail("<br>" + result.getThrowable() + "<br>");
            }
        }
    }

    // Other methods can remain empty or be expanded as needed
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
