package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import config.drivers.AndroidDriverManager;
import config.drivers.SeleniumDriverManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Arrays;

import static utils.ScreenshotUtil.takeScreenshot;

public class ExtentTestNGListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    public static StringWriter requestWriter = new StringWriter();
    public static PrintStream requestCapture = new PrintStream(new WriterOutputStream(requestWriter), false);

    public static StringWriter responseWriter = new StringWriter();
    public static PrintStream responseCapture = new PrintStream(new WriterOutputStream(responseWriter), false);

    private static String log;
    private static String responseLog;
    private static String requestLog;
    public static String testCategory;
    public static String testDetails;
    public static String screenshotName;

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
        switch (testCategory) {
            case "API":
                testThread.get().info("<pre><center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center></br></br>" + getRequestLog() + "</br></pre>");
                testThread.get().pass("<pre><center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center></br></br>" + getResponseLog() + "</br></pre>");
                break;
            case "WEB", "MOBILE":
                if (testCategory.equalsIgnoreCase("WEB")) {
                    testThread.get().pass("<font color=" + "green>" + testDetails + "</font>", MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.captureScreenshot(SeleniumDriverManager.getSeleniumDriver())).build());
                } else {
                    testThread.get().pass("<font color=" + "green>" + testDetails + "</font>", MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.captureScreenshot(BaseTest.appiumDriver)).build());
                }
                break;
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        testThread.get().assignCategory(testCategory);

        switch (testCategory) {
            case "API":
                testThread.get().info("<pre><center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center></br></br>" + getRequestLog() + "</br></pre>");
                testThread.get().fail("<pre><center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center></br></br>" + getResponseLog() + "</br>" + result.getThrowable().getMessage() + "</br></pre>");
                break;
            case "WEB", "MOBILE":
                String exceptionSummary = throwable.getMessage();
                String excepionMessage = Arrays.toString(result.getThrowable().getStackTrace());

                try {
                    if (testCategory.equalsIgnoreCase("WEB")) {
                        ExtentManager.captureScreenshot(SeleniumDriverManager.getSeleniumDriver());
                        testThread.get().fail("<font color=" + "red>" + testDetails + "</br></br>" + exceptionSummary + "</br><details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured: Click to see"
                                + "</font>" + "</b >" + "</summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>" + " \n" + "<br><br>Screenshot of failure" + "</font>", MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.captureScreenshot(SeleniumDriverManager.getSeleniumDriver()).toString()).build());
                    } else {
                        ExtentManager.captureScreenshot(BaseTest.appiumDriver);
                        testThread.get().fail("<font color=" + "red>" + testDetails + "</br></br>" + exceptionSummary + "</br><details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured: Click to see"
                                + "</font>" + "</b >" + "</summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>" + " \n" + "<br><br>Screenshot of failure" + "</font>", MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.captureScreenshot(BaseTest.appiumDriver).toString()).build());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Object testInstance = result.getInstance();
                try {
                    java.lang.reflect.Method m = testInstance.getClass().getMethod("getDriver");
                    Object driver = m.invoke(testInstance);
                    if (driver != null) {
                        screenshotName = takeScreenshot((org.openqa.selenium.WebDriver) driver, result.getMethod().getMethodName());
                        testThread.get().addScreenCaptureFromPath(screenshotName, "Failed Screenshot");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());                }
                break;
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