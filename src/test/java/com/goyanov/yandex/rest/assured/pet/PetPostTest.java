package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

@DisplayName("Post методы питомцев (RestAssured)")
public class PetPostTest extends AssuredTest
{
    @ParameterizedTest
    @ValueSource(longs = {3L})
    @DisplayName("Успешное добавление питомца")
    public void postPet_Successful(Long id)
    {
        given().
                contentType(ContentType.JSON).
                body(new Pet().id(id).name("Zveryok")).
                post("/pet/").then().statusCode(200);
    }

    @Test
    @DisplayName("Возврат статуса 405 при попытке добавления невалидного питомца")
    public void postPet_InvalidInput()
    {
        given().
                contentType(ContentType.JSON).
                post("/pet/").then().statusCode(405);
    }

    @Step("Шаг 1 (добавление питомца)")
    private void addPet(Long petId)
    {
        postPet_Successful(petId);
    }

    @Step("Шаг 2 (обновление питомца)")
    public void updatePet(Long petId, String name, Pet.StatusEnum status)
    {
        given().
                contentType(ContentType.JSON).
                body(new Pet().id(petId).name(name).status(status)).
                post("/pet").then().statusCode(200);
    }

    @Test
    @DisplayName("Успешное обновление существующего питомца")
    public void postPetById_Successful()
    {
        addPet(56L);
        updatePet(56L, "Kolkk", Pet.StatusEnum.SOLD);
    }

    @Test
    @DisplayName("Возврат статуса 405 при обновлении питомца с указанием невалидных данных")
    public void postPetById_InvalidInput()
    {
        given().contentType(ContentType.JSON).body(new Pet().id(null).name("Grisha")).then().statusCode(405);
    }
}
