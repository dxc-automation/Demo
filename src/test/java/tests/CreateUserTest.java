package tests;

import com.google.gson.JsonObject;
import config.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
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

        test.info("<pre><center><b>REQUEST</b></center></br>" +  + "</br>" + responseBody + "</pre>");

        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(body.toString())
                .when()
                .post("/api/users");

        String responseBody = response.prettyPrint();

        try {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");

            String name = response.jsonPath().getString("name");
            String job = response.jsonPath().getString("job");

            Assert.assertEquals(name, "Test User");
            Assert.assertEquals(job, "Automation Tester");

            Assert.assertNotNull(response.jsonPath().getString("id"));
            Assert.assertNotNull(response.jsonPath().getString("createdAt"));

            test.info("<pre><center><b>RESPONSE</b></center></br>" + response.getStatusCode() + "</br>" + responseBody + "</pre>");
        } catch (AssertionError e) {
            test.fail("Test failed: " + e.getMessage());
            throw e;
        }
    }
}

