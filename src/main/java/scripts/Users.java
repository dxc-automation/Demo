package scripts;

import com.google.gson.JsonObject;
import data.Endpoints;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import static config.ExtentTestNGListener.*;

public class Users {

    public Response POST_NewUser(String name, String job) throws Exception {
        JsonObject body = new JsonObject();
        body.addProperty("name", name);
        body.addProperty("job", job);

        Response response =  RestAssured.given()
                .baseUri(Endpoints.publicHost)
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(body.toString())
                .when()
                .post("/api/users");

        setRequestLog(writeRequestLog());
        setResponseLog(writeResponseLog());

        return response;
    }
}
