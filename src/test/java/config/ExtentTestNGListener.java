package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.demo.ScreenshotUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static config.BaseTest.driver;
import static org.demo.ScreenshotUtil.*;

public class ExtentTestNGListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    public static StringWriter requestWriter  = new StringWriter();
    public static PrintStream  requestCapture = new PrintStream(new WriterOutputStream(requestWriter), false);

    public static StringWriter responseWriter  = new StringWriter();
    public static PrintStream responseCapture = new PrintStream(new WriterOutputStream(responseWriter), false);

    private static String log;
    private static String responseLog;
    private static String requestLog;
    public  static String testCategory;
    public  static String testPassDetails;
    public  static String screenshotName;

    public static void setRequestLog(String newRequestLog) {
        requestLog = newRequestLog;
    }
    public static String getRequestLog() {
        return requestLog;
    }

    public static void setResponseLog(String newResponseLog) {
        responseLog = newResponseLog;
    }

    public static String getResponseLog() {
        return responseLog;
    }

    public static String writeRequestLog() throws IOException {
        log = requestWriter.toString();
        requestWriter.getBuffer().setLength(0);
        return log;
    }

    public static String writeResponseLog() {
        log = responseWriter.toString();
        responseWriter.getBuffer().setLength(0);
        return log;
    }

    @Override
    public void onStart(ITestContext context) {
        // optional: може да се добави настройка тук
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        Test testAnnotation = result.getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(Test.class);

        String testName = testAnnotation.testName();
        if (testName == null || testName.isEmpty()) {
            testName = result.getMethod().getMethodName(); // fallback
        }

        System.out.println(testName);
        ExtentTest test = extent.createTest(testName);
        testThread.set(test);
        testCategory = result.getMethod().getDescription();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().assignCategory(testCategory);

        if (testCategory.equalsIgnoreCase("API")) {
            testThread.get().info("<pre><center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center></br></br>"   + getRequestLog() + "</br></pre>");
            testThread.get().pass("<pre><center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center></br></br>" + getResponseLog() + "</br></pre>");
        } else if (testCategory.equalsIgnoreCase("WEB")) {
            try {
                System.out.println(screenshotName);
                testThread.get().log(Status.PASS, "<center>SCREENSHOT</center>" , MediaEntityBuilder.createScreenCaptureFromPath("../" + screenshotName).build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        testThread.get().assignCategory(testCategory);

        if (testCategory.equalsIgnoreCase("API")) {
            testThread.get().info("<pre><center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center></br></br>"   + getRequestLog() + "</br></pre>");
            testThread.get().fail("<pre><center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center></br></br>" + getResponseLog() + "</br>" + result.getThrowable().getMessage() + "</br></pre>");
        } else if(testCategory.equalsIgnoreCase("WEB")) {
                try {
                    screenshotName = screenshot(driver, result.getName());
                    System.out.println("🔻 Screenshot for failed test saved at: " + screenshotName);
                    testThread.get().log(Status.FAIL, "<pre><br><b>FAILED ON SCREEN</b><br>", MediaEntityBuilder.createScreenCaptureFromPath("../" + screenshotName).build());
                    testThread.get().log(Status.FAIL, "<pre><br>" + throwable + "<br></pre>");
                    driver.quit();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

        Object testInstance = result.getInstance();
        try {

            java.lang.reflect.Method m = testInstance.getClass().getMethod("getDriver");
            Object driver = m.invoke(testInstance);
            if (driver != null) {
                screenshotName = takeScreenshot((org.openqa.selenium.WebDriver) driver, result.getMethod().getMethodName());
                testThread.get().addScreenCaptureFromPath(screenshotName, "Failed Screenshot");
            }
        } catch (NoSuchMethodException e) {
            // няма getDriver - игнорирай или логни
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().skip("Test skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not commonly used
    }
}