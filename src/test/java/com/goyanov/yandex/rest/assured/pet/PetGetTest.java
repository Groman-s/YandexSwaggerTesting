package com.goyanov.yandex.rest.assured.pet;

import io.restassured.RestAssured;
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
