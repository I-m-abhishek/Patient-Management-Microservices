import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
    }

//      THERE ARE 3 STEPS
//    1. arrange
//    2. act
//    3. assert
    @Test
    public void shouldReturnOkWithValidToken() {
            String loginPayload= """
                    {
                    "email":"admin@google.com",
                    "password": "password"
                    }
                    """;
        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when().post("/auth/login")
                .then()
                .statusCode(200)
                .body("token" , notNullValue())
                .extract().response();

        System.out.println("Generated Token: " + response.jsonPath().getString("token") );

    }

    @Test
    public void shouldReturnUnauthorizedCredentials() {
        String loginPayload= """
                    {
                    "email":"admin@google.com",
                    "password": "password-w"
                    }
                    """;
       given()
                .contentType("application/json")
                .body(loginPayload)
                .when().post("/auth/login")
                .then()
                .statusCode(401);


        System.out.println("Unauthorized Credentials "  );

    }
}
