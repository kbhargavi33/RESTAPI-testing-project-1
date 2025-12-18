package Tests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojos.CartProduct;
import pojos.CartResponse;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import base.ConfigReader;

import java.util.List;
import java.util.Map;

public class DummyJsonEcommerceTest extends BaseTest{


    String token;
    int userId = 1;
    CartResponse cartResponse;  // Object to store POST cart response



    @Test(priority = 1)
    public void loginUser() {
        String payload = "{ \"username\": \"" + ConfigReader.get("username") +
                "\", \"password\": \"" + ConfigReader.get("password") + "\" }";

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().response();

        token = response.jsonPath().getString("accessToken");
        Assert.assertNotNull(token, "Token should not be null");
    }

    @Test(priority = 2)
    public void getAllProducts() {
        Response response = RestAssured
                .given()
                .get("/products")
                .then()
                .statusCode(200)
                .extract().response();

        List<Map<String, Object>> products = response.jsonPath().getList("products");
        Assert.assertTrue(products.size() > 0, "Products list should not be empty");
    }

    @Test(priority = 3)
    public void getSingleProduct() {
        int productId = 2;
        Response response = RestAssured
                .given()
                .get("/products/" + productId)
                .then()
                .statusCode(200)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        Assert.assertEquals(id, productId, "Product ID should match");
    }

    @Test(priority = 4)
    public void addProductsToCart() {
    	String payload = "{ \"userId\": " + userId +
                ", \"products\": [{ \"id\": 2, \"quantity\": 1 }, { \"id\": 3, \"quantity\": 1 }] }";
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/carts/add")
                .then()
                .statusCode(201)
                .extract().response();

        // Capture the response into a local object
        cartResponse = response.as(CartResponse.class);

        Assert.assertNotNull(cartResponse, "Cart response should not be null");
        Assert.assertEquals(cartResponse.getProducts().size(), 2, "Cart should contain 2 products");
    }

    
    @Test(priority = 5)
    public void validateCartProducts() {
        // Validate using the captured object instead of GET by user
        List<CartProduct> products = cartResponse.getProducts();

        boolean hasProduct2 = products.stream().anyMatch(p -> p.getId() == 2);
        boolean hasProduct3 = products.stream().anyMatch(p -> p.getId() == 3);

        Assert.assertTrue(hasProduct2, "Cart should contain product 2");
        Assert.assertTrue(hasProduct3, "Cart should contain product 3");
        Assert.assertEquals(cartResponse.getUserId(), userId, "Cart userId should match");
    }


}
