package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PetPostTest
{
    @BeforeAll
    public static void initPath()
    {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void postPet_Successful()
    {
        given().
                contentType(ContentType.JSON).
                body(new Pet().id(3L).name("Zveryok")).
                post("/pet/").then().statusCode(200);
    }

    @Test
    public void postPet_InvalidInput()
    {
        given().
                contentType(ContentType.JSON).
                post("/pet/").then().statusCode(405);
    }
}
