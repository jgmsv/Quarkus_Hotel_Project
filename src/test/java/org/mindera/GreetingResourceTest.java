package org.mindera;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
class GreetingResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/api/v1/hotel")
                .then()
                .statusCode(200)
                .body("rooms[0].roomNumber", containsInAnyOrder(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

}