package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.io.File;

import static config.BaseTest.driver;

public class ExtentManager {

    private static ExtentReports extent;
    public static String screenshotFile;


    public synchronized static ExtentReports getInstance() {
        String reportName = "Automation_Report.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./test-output/" + reportName);//specify location of the report

        sparkReporter.config().setDocumentTitle("Automation Report"); // Title of report
        sparkReporter.config().setReportName("Automation Report"); // name of the report
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setProtocol(Protocol.HTTPS);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environemnt", "QA");

        return extent;
    }

    public static String captureScreenshot() {
        String screenshotPath = null;
        try {
            //take screenshot and save it in a file
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            //copy the file to the required path
            File destinationFile = new File(System.getProperty("user.dir") + "/test-output/screenshots/" + System.currentTimeMillis() + ".png");
            FileHandler.copy(sourceFile, destinationFile);
            String[] relatvePath = destinationFile.toString().split("screenshots");
            screenshotPath = "../" + relatvePath[1];
        } catch (Exception e) {
            System.out.println("Failure to take screenshot " + e);
        }
        return screenshotPath;
    }
}