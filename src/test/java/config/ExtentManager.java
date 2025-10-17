package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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
        try
        {
            Date d = new Date();
            String screenshotFileName = d.toString().replace(":", "_").replace(" ", "_") + ".png";

            File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("./test-output/screenshots/" + screenshotFileName);
            screenshotFile = destination.getAbsolutePath();

            FileUtils.copyFile(source, destination);
            System.out.println("Successfully captured a screenshot");
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
        return screenshotFile;
    }
}