package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReport() {
        if (extent == null) {
            ExtentSparkReporter spark =
                    new ExtentSparkReporter("test-output/ExtentReport.html");

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
