package tests;

import com.google.gson.JsonObject;
import config.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class CreateUserTest extends BaseTest {

    @Test
    public void testCreateUser() {
        test = extent.createTest("Create User Test").assignCategory("API");

        RestAssured.baseURI = "https://reqres.in";

        JsonObject body = new JsonObject();
        body.addProperty("name", "Test User");
        body.addProperty("job", "Automation Tester");

        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(body.toString())
                .when()
                .post("/api/users");

        // Log response
        test.info("Response Status Code: <b>" + response.getStatusCode() + "</b>");
        test.info("Response Body</br><b>" + response.getBody().asPrettyString() + "</b>");

        try {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");
            test.pass("Status code is 201 as expected");

            String name = response.jsonPath().getString("name");
            String job = response.jsonPath().getString("job");

            Assert.assertEquals(name, "Test User");
            Assert.assertEquals(job, "Automation Tester");

            Assert.assertNotNull(response.jsonPath().getString("id"));
            Assert.assertNotNull(response.jsonPath().getString("createdAt"));

            test.pass("All response fields validated successfully");
        } catch (AssertionError e) {
            test.fail("Test failed: " + e.getMessage());
            throw e;
        }
    }
}

