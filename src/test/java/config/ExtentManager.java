package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;


    public synchronized static ExtentReports getInstance() {
        String reportName = "Automation_Report.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./test-output/" + reportName);//specify location of the report

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
        extent.setSystemInfo("Environemnt", "QA");

        return extent;
    }
}
