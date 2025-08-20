package org.yehorychev;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqResApiTests extends BaseTest {

    @Test
    public void listUsers_shouldReturnPage2With200() {
        given()
                .queryParam("page", 2)
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", not(empty()))
                .body("per_page", greaterThan(0));
    }

    @Test
    public void getSingleUser_shouldContainExpectedEmail() {
        given()
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .body("support.url", containsString("reqres"));
    }

    @Test
    public void createUser_shouldReturn201AndId() {
        String payload = """
                {
                  "name": "morpheus",
                  "job": "leader"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .body("id", not(emptyOrNullString()))
                .body("createdAt", not(emptyOrNullString()));
    }

    @Test
    public void updateUser_put_shouldUpdateJob() {
        String payload = """
                {
                  "name": "morpheus",
                  "job": "zion resident"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"))
                .body("updatedAt", not(emptyOrNullString()));
    }

    @Test
    public void deleteUser_shouldReturn204() {
        given()
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    public void registerUser_shouldReturnToken() {
        String payload = """
                {
                  "email": "eve.holt@reqres.in",
                  "password": "pistol"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/register")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", not(emptyOrNullString()));
    }
}