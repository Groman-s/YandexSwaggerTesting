package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Get методы питомцев (RestAssured)")
public class PetGetTest extends AssuredTest
{
    @Step("Шаг 1 (добавление питомца)")
    public void addPet(Long id)
    {
        RestAssured.given().
                contentType(ContentType.JSON).
                body(new Pet().id(id).name("Leika").status(Pet.StatusEnum.SOLD)).
                post("/pet");
    }

    @Step("Шаг 2 (получение питомца)")
    public Pet getPetWithStatus200(Long id) {
        return RestAssured.given()
                .pathParam("petId", id)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200) // Проверка, что статус код 200
                .extract()
                .as(Pet.class); // Извлечение объекта Pet из ответа
    }

    @Test
    @DisplayName("Успешное получение питомца по ID")
    public void getPetById_Successful()
    {
        addPet(7366L);
        Pet pet = getPetWithStatus200(7366L);
        assertEquals(7366L, pet.getId());
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
