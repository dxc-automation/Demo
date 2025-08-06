package tests.api;

import config.BaseTest;
import config.TestListener;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import scripts.Users;

import static io.restassured.RestAssured.given;


public class UserTests extends BaseTest {

    private Users users = new Users();


    @Test(testName = "[POST] Create User", description = "API")
    public void testCreateUser(String expectedUserName, String expectedUserJob) throws Exception {
        Response response = users.POST_NewUser(expectedUserName, expectedUserJob);

        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");

        String actualUserName = response.jsonPath().getString("name");
        String actualUserJob = response.jsonPath().getString("job");

        Assert.assertEquals(actualUserName, expectedUserName);
        Assert.assertEquals(actualUserJob, expectedUserJob);

        Assert.assertNotNull(response.jsonPath().getString("id"));
        Assert.assertNotNull(response.jsonPath().getString("createdAt"));
    }
}

