package com.targa.labs.dev.rest;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@QuarkusTest
@QuarkusTestResource(DatabaseTestResource.class)
public class BookResourceTest {

    public static final String BOOKS_API_URL = "/api/books";

    @Test
    public void findAll() {
        given()
                .when().get(BOOKS_API_URL)
                .then()
                .statusCode(200);
    }

    @Test
    void testFindAll() {
        given().when()
                .get(BOOKS_API_URL)
                .then()
                .statusCode(OK.getStatusCode())
                .body("size()", greaterThan(0))
                .body(containsString("title"))
                .body(containsString("isbn"))
                .body(containsString("author"))
                .body(containsString("price"));
    }

    @Test
    void testFindById() {
        given().when()
                .get(BOOKS_API_URL + "/1")
                .then()
                .statusCode(OK.getStatusCode())
                .body(containsString("Pairing Apache Shiro and Java EE 7"))
                .body(containsString("978-1-365-12404-4"))
                .body(containsString("Nebrass Lamouchi"))
                .body(containsString("0"));
    }

    @Test
    void testCreate() {
        var requestParams = new HashMap<>();
        requestParams.put("title", "Pro Java Microservices with Quarkus and Kubernetes");
        requestParams.put("isbn", "978-1-4842-7170-4");
        requestParams.put("author", "Nebrass Lamouchi");
        requestParams.put("price", 39.99);

        var newBookId = given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(requestParams)
                .post(BOOKS_API_URL)
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .jsonPath()
                .getInt("id");

        assertNotEquals(newBookId, 0);

        given().when()
                .get(BOOKS_API_URL + "/" + newBookId)
                .then()
                .statusCode(OK.getStatusCode())
                .body(containsString("Pro Java Microservices with Quarkus and Kubernetes"))
                .body(containsString("978-1-4842-7170-4"))
                .body(containsString("Nebrass Lamouchi"))
                .body(containsString("39.99"));

        given().when()
                .delete(BOOKS_API_URL + "/" + newBookId)
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

    @Test
    void testDelete() {
        given()
                .delete(BOOKS_API_URL + "/2")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

    @Test
    void testFindByAuthorName() {
        var ids = given().when()
                .get(BOOKS_API_URL + "/author/nebrass")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .jsonPath()
                .getList("id", Long.class);

        assertNotEquals(ids.size(), 0);
    }
}
