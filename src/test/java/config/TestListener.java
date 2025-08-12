package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
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
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import static config.BaseTest.driver;
import static config.BaseTest.testName;


public class TestListener implements ITestListener {

    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest test;


    public static StringWriter requestWriter  = new StringWriter();
    public static PrintStream  requestCapture = new PrintStream(new WriterOutputStream(requestWriter), false);

    public static StringWriter responseWriter  = new StringWriter();
    public static PrintStream  responseCapture = new PrintStream(new WriterOutputStream(responseWriter), false);

    private static String log;
    private static String responseLog;
    private static String requestLog;
    private static String testCategory;
    public  static String testPassDetails;
    public  static String screenshot;


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
    public void onStart(ITestContext testContext)
    {
        String reportName="Automation_Report.html";

        sparkReporter=new ExtentSparkReporter("./test-output/" + reportName);//specify location of the report

        sparkReporter.config().setDocumentTitle("Automation Report"); // Title of report
        sparkReporter.config().setReportName("Automation Report"); // name of the report
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().enableTimeline(true);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setProtocol(Protocol.HTTPS);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environemnt","QA");
    }

    public void onTestSuccess(ITestResult result) {
        testCategory = result.getMethod().getDescription();

        test = extent.createTest(testName);
        test.assignCategory(testCategory);

        if (testCategory.equalsIgnoreCase("API")) {
            test.info("<pre><center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center></br></br>"   + getRequestLog() + "</br></pre>");
            test.pass("<pre><center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center></br></br>" + getResponseLog() + "</br></pre>");
        } else if (testCategory.equalsIgnoreCase("WEB")) {
            try {
                test.pass(testPassDetails, MediaEntityBuilder.createScreenCaptureFromPath("../" + screenshot).build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void onTestFailure(ITestResult result) {
        testCategory = result.getMethod().getDescription();

        test = extent.createTest(testName);
        test.assignCategory(testCategory);

        if (testCategory.equalsIgnoreCase("API")) {
            test.info("<pre><center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center></br></br>"   + getRequestLog() + "</br></pre>");
            test.fail("<pre><center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center></br></br>" + getResponseLog() + "</br>" + result.getThrowable().getMessage() + "</br></pre>");
        } else if(testCategory.equalsIgnoreCase("WEB")) {
            String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String fileName = "test-output/screenshots/" + result.getName() + "_failed.png";
            File destination = new File(fileName);
            destination.getParentFile().mkdirs(); // create dirs if not exists
            if (screenshotPath != null) {
                try {
                    Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("🔻 Screenshot for failed test saved at: " + screenshotPath);
                test.fail("<br><b>FAILED ON SCREEN</b><br>", MediaEntityBuilder.createScreenCaptureFromPath("../" + fileName).build());
                test.fail("<br>" + result.getThrowable() + "<br>");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void onTestSkipped(ITestResult result)
    {
        testCategory = result.getMethod().getDescription();

        test = extent.createTest(testName);
        test.assignCategory(testCategory);

        test.info("<pre><center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center></br></br>"   + getRequestLog()  + "</br></pre>");
        test.skip("<pre><center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center></br></br>" + getResponseLog() + "</br>" + result.getThrowable().getMessage() + "</br></pre>");
    }


    public void onFinish(ITestContext testContext)
    {
        extent.flush();
    }
}