package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class PetGetTest
{
    @BeforeAll
    public static void initPath()
    {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void getPetById_Successful()
    {
        RestAssured.given().
                contentType(ContentType.JSON).
                body(new Pet().id(500L).name("Leika").status(Pet.StatusEnum.SOLD)).
                post("/pet");

        RestAssured.get("/pet/500").then()
                .body("id", equalTo(500))
                .statusCode(200);
    }

    @Test
    public void getPetById_NotFound()
    {
        RestAssured.get("/pet/0").then().statusCode(404);
    }

    @Test
    public void getPetById_InvalidId()
    {
        RestAssured.get("/pet/-hudwa").then().statusCode(400);
    }
}
