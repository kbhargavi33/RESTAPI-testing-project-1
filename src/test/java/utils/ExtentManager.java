package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReport() {
        if (extent == null) {

            String reportDir = System.getProperty("user.dir")
                    + "/target/extent-reports";

            // Create directory if it doesn't exist
            File dir = new File(reportDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String reportPath = reportDir + "/ExtentReport.html";

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(reportPath);

            spark.config().setReportName("DummyJSON API Automation Report");
            spark.config().setDocumentTitle("API Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Project", "DummyJSON E-Commerce");
            extent.setSystemInfo("Tester", "Bhargavi");
            extent.setSystemInfo("Framework", "RestAssured + TestNG");
        }
        return extent;
    }
}
