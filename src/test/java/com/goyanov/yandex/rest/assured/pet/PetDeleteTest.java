package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Delete методы питомцев (RestAssured)")
public class PetDeleteTest extends AssuredTest
{
    @Step("Шаг 1 (добавление питомца)")
    private void addPet(Long petId)
    {
        RestAssured.given().contentType(ContentType.JSON).body(new Pet().id(petId)).post("/pet");
    }

    @Step("Шаг 2 (удаление питомца)")
    public void removePet(Long petId)
    {
        RestAssured.delete("/pet/" + petId).then().statusCode(200);
    }

    @Test
    @DisplayName("Успешное удаление питомца по ID")
    public void deletePetById_Successful()
    {
        addPet(6189L);
        removePet(6189L);
    }

    @Test
    @DisplayName("Возврат статуса 404 при удалении по не существующему ID")
    public void deletePetById_NotFound()
    {
        RestAssured.delete("/pet/-129387918").then().statusCode(404);
    }

    @Test
    @DisplayName("Возврат статуса 400 при указании невалидного ID во время удаления")
    public void deletePetById_InvalidId()
    {
        RestAssured.delete("/pet/-42324e23qhguyg7").then().statusCode(400);
    }
}
