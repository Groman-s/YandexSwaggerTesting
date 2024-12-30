package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Category;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DisplayName("Put методы питомцев (RestAssured)")
public class PetPutTest
{
    @BeforeAll
    public static void initPath()
    {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    @DisplayName("Успешное обновление питомца (вариант 1)")
    public void putPet_Successful1()
    {
        given().contentType(ContentType.JSON).body(new Pet().id(4L)).post("/pets");

        RestAssured.given().
                contentType(ContentType.JSON).
                body(new Pet().id(4L).name("Rechka")).
                put("/pet").then().statusCode(200);
    }

    @Test
    @DisplayName("Успешное обновление питомца (вариант 2)")
    public void putPet_Successful2()
    {
        given().contentType(ContentType.JSON).body(new Pet().id(300L)).post("/pets");

        String body =
        """
            {
                "id": 300
            }
        """;
        RestAssured.given().
                contentType(ContentType.JSON).
                body(body).
                put("/pet").then().statusCode(200);
    }

    @Test
    @DisplayName("Возврат статуса 404 при обновлении по не существующему ID")
    @Severity(SeverityLevel.CRITICAL)
    public void putPet_NotFound()
    {
        RestAssured.given().
                contentType(ContentType.JSON).
                body(new Pet().id(0L).name("Kochka")).
                put("/pet").then().statusCode(404);
    }
}
