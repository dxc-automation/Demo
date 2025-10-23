package scripts;

import com.google.gson.JsonObject;
import data.Endpoints;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import static config.ExtentTestNGListener.*;

public class Books {

    public static class Data {

        private int bookId;
        private String bookTitle;
        private String bookAuthor;
        private String bookDescription;


        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public void setBookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
        }

        public String getBookAuthor() {
            return bookAuthor;
        }

        public void setBookAuthor(String bookAuthor) {
            this.bookAuthor = bookAuthor;
        }

        public String getBookDescription() {
            return bookDescription;
        }

        public void setBookDescription(String bookDescription) {
            this.bookDescription = bookDescription;
        }
    }


    public Response GET_Books(String bookTitle) throws Exception {
        Response response = RestAssured.given()
                .baseUri(Endpoints.localHost)
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .header("Content-Type", "application/json")
                .when()
                .get("/api/books?title=" + bookTitle);

        setRequestLog(writeRequestLog());
        setResponseLog(writeResponseLog());

        return response;
    }


    public Response POST_NewBook(String bookTitle, int bookId, String bookAuthor, String bookDescription) throws Exception {
        JsonObject body = new JsonObject();
        body.addProperty("Id", bookId);
        body.addProperty("Author", bookAuthor);
        body.addProperty("Title", bookTitle);
        body.addProperty("Description", bookDescription);

        Response response =  RestAssured.given()
                .baseUri(Endpoints.localHost)
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .header("Content-Type", "application/json")
                .body(body.toString())
                .when()
                .post("/api/books");

        setRequestLog(writeRequestLog());
        setResponseLog(writeResponseLog());

        return response;
    }


    public Response PUT_UpdateBook(String bookTitle, int bookId, String bookAuthor, String bookDescription) throws Exception {
        JsonObject body = new JsonObject();
        body.addProperty("Id", bookId);
        body.addProperty("Author", bookAuthor);
        body.addProperty("Title", bookTitle);
        body.addProperty("Description", bookDescription);

        Response response = RestAssured.given()
                .baseUri(Endpoints.localHost)
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .header("Content-Type", "application/json")
                .body(body.toString())
                .when()
                .put("/api/books/" + bookId);

        setRequestLog(writeRequestLog());
        setResponseLog(writeResponseLog());

        return response;
    }


    public Response DELETE_Book(int bookId) throws Exception {
        Response response = RestAssured.given()
                .baseUri(Endpoints.localHost)
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .header("Content-Type", "application/json")
                .when()
                .delete("/api/books/" + bookId);

        setRequestLog(writeRequestLog());
        setResponseLog(writeResponseLog());

        return response;
    }
}
