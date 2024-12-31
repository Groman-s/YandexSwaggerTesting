package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Delete методы питомцев (RestAssured)")
public class PetDeleteTest
{
    @BeforeAll
    public static void initPath()
    {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    @DisplayName("Успешное удаление питомца по ID")
    public void deletePet_Successful()
    {
        RestAssured.given().contentType(ContentType.JSON).body(new Pet().id(6189L)).post("/pet");
        RestAssured.delete("/pet/6189").then().statusCode(200);
    }

    @Test
    @DisplayName("Возврат статуса 404 при удалении по не существующему ID")
    public void deletePet_NotFound()
    {
        RestAssured.delete("/pet/-129387918").then().statusCode(404);
    }

    @Test
    @DisplayName("Возврат статуса 400 при указании невалидного ID во время удаления")
    public void deletePet_InvalidId()
    {
        RestAssured.delete("/pet/-42324e23qhguyg7").then().statusCode(400);
    }
}
