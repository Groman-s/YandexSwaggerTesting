package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DisplayName("Post методы питомцев (RestAssured)")
public class PetPostTest extends AssuredTest
{
    @Test
    @DisplayName("Успешное добавление питомца")
    public void postPet_Successful()
    {
        given().
                contentType(ContentType.JSON).
                body(new Pet().id(3L).name("Zveryok")).
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
}
