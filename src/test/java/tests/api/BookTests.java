package tests.api;

import config.BaseTest;
import config.TestListener;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import scripts.Books;

public class BookTests extends BaseTest {

    private Books books = new Books();
    private Books.Data data = new Books.Data();


    @Test(testName = "[GET] List All Books", description = "API", priority = 0)
    public void listAllBooks() throws Exception {
        Response response = books.GET_Books("");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(testName = "[POST] Add New Book", description = "API", priority = 1)
    public void addNewBook() throws Exception {
        data.setBookId(1);
        data.setBookTitle("New Book");
        data.setBookAuthor("Anonymous");
        data.setBookDescription("Horror");

        Response response = books.POST_NewBook(data.getBookTitle(), data.getBookId(), data.getBookAuthor(), data.getBookDescription());
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 201");
    }

    @Test(testName = "[PUT] Update Book", description = "API", priority = 2)
    public void updateBook() throws Exception {
        data.setBookTitle("Updated Book Title");
        data.setBookAuthor("Updated Author");
        data.setBookDescription("Updated Description");

        Response response = books.PUT_UpdateBook(data.getBookTitle(), data.getBookId(), data.getBookAuthor(), data.getBookDescription());
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(testName = "[DELETE] Remove Book", description = "API", priority = 3)
    public void deleteBook() throws Exception {
        Response response = books.DELETE_Book(data.getBookId());
        Assert.assertEquals(response.getStatusCode(), 204, "Expected status code 200");
    }
}

