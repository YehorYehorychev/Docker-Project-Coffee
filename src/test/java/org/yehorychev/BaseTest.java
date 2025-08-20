package org.yehorychev;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";

        String apiKey = System.getenv("REQRES_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            apiKey = System.getProperty("reqres.api.key", "reqres-free-v1");
        }

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("x-api-key", apiKey)
                .build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}