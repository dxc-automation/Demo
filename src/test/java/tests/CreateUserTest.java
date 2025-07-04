package tests;

import com.google.gson.JsonObject;
import config.BaseTest;
import data.Endpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.demo.Utilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class CreateUserTest extends BaseTest {

    @Test
    public void testCreateUser() {
        test = extent.createTest("Create User Test").assignCategory("API");

        RestAssured.baseURI = Endpoints.baseURI;

        JsonObject body = new JsonObject();
        body.addProperty("name", "Test User");
        body.addProperty("job", "Automation Tester");

        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(body.toString())
                .when()
                .post("/api/users");

        test.info("<pre><center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center></br>"
                + Endpoints.baseURI + "</br></br>"
                + Utilities.getFormattedJson(body) + "</br></pre>");

        String responseBody = response.prettyPrint();

        try {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");

            String name = response.jsonPath().getString("name");
            String job = response.jsonPath().getString("job");

            Assert.assertEquals(name, "Test User");
            Assert.assertEquals(job, "Automation Tester");

            Assert.assertNotNull(response.jsonPath().getString("id"));
            Assert.assertNotNull(response.jsonPath().getString("createdAt"));

            test.pass("<pre><center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center></br>"
                    + "Response code: " + response.getStatusCode() + "</br></br>" + responseBody + "</pre>");

        } catch (AssertionError e) {
            test.fail("Test failed: " + e.getMessage());
            throw e;
        }
    }
}

