package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Category;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PetPutTest
{
    @BeforeAll
    public static void initPath()
    {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void putPet_Successful1()
    {
        RestAssured.given().
                contentType(ContentType.JSON).
                body(new Pet().id(4L).name("Rechka")).
                put("/pet").then().statusCode(200);
    }

    @Test
    public void putPet_Successful2()
    {
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
    public void putPet_NotFound()
    {
        RestAssured.given().
                contentType(ContentType.JSON).
                body(new Pet().id(0L).name("Kochka")).
                put("/pet").then().statusCode(404);
    }
}
