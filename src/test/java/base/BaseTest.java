package base;

import io.restassured.RestAssured;
import utils.ExtentManager;

import org.testng.annotations.*;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        RestAssured.baseURI = ConfigReader.get("baseURL");
        System.out.println("Before Suite");
    }

    @AfterSuite
    public void closeReport() {
        ExtentManager.getExtentReport().flush();
    }

}
