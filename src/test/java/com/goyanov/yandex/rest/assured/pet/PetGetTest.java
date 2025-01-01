package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Get методы питомцев (RestAssured)")
public class PetGetTest
{
    @BeforeAll
    public static void initPath()
    {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Step("Шаг 1 (добавление питомца)")
    public void addPet(Long id)
    {
        RestAssured.given().
                contentType(ContentType.JSON).
                body(new Pet().id(id).name("Leika").status(Pet.StatusEnum.SOLD)).
                post("/pet");
    }

    @Step("Шаг 2 (получение питомца)")
    public void getPetWithStatus200(Long id)
    {
        RestAssured.get("/pet/" + id).then()
                .body("id", equalTo(id.intValue()))
                .body("name", equalTo("Leika"))
                .body("status", equalTo("sold"))
                .statusCode(200);
    }

    @Test
    @DisplayName("Успешное получение питомца по ID")
    public void getPetById_Successful()
    {
        addPet(7366L);
        getPetWithStatus200(7366L);
    }

    @Test
    @DisplayName("Возврат статуса 404 при удалении по не существующему ID")
    public void getPetById_NotFound()
    {
        RestAssured.get("/pet/0").then().statusCode(404);
    }

    @Test
    @DisplayName("Возврат статуса 400 при указании невалидного ID во время удаления")
    public void getPetById_InvalidId()
    {
        RestAssured.get("/pet/-hudwa").then().statusCode(400);
    }
}
