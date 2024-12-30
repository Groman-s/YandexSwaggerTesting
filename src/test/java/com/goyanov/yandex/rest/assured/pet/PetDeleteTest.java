package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PetDeleteTest
{
    @BeforeAll
    public static void initPath()
    {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void deletePet_Successful()
    {
        RestAssured.given().contentType(ContentType.JSON).body(new Pet().id(6189L)).post("/pet");
        RestAssured.delete("/pet/6189").then().statusCode(200);
    }

    @Test
    public void deletePet_NotFound()
    {
        RestAssured.delete("/pet/-129387918").then().statusCode(404);
    }

    @Test
    public void deletePet_InvalidId()
    {
        RestAssured.delete("/pet/-hguyg7").then().statusCode(400);
    }
}
