package com.goyanov.yandex.rest.assured.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@DisplayName("Put методы питомцев (RestAssured)")
public class PetPutTest
{
    @BeforeAll
    public static void initPath()
    {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    private static Stream<Object> responseBodiesForPut()
    {
        return Stream.of
        (
            new Pet().id(4L).name("Rechka"),
            """
                {
                    "id": 4
                }
            """
        );
    }

    @Step("Шаг 1 (добавление питомца)")
    public void addPetWithId4()
    {
        given().contentType(ContentType.JSON).body(new Pet().id(4L)).post("/pets");
    }

    @Step("Шаг 2 (обновление питомца)")
    public void putBodyWithId4(Object body)
    {
        RestAssured.given().
                contentType(ContentType.JSON).
                body(body).
                put("/pet").then().statusCode(200);
    }

    @ParameterizedTest
    @MethodSource("responseBodiesForPut")
    @DisplayName("Успешное обновление питомца")
    public void putPet_Successful(Object body)
    {
        addPetWithId4();
        putBodyWithId4(body);
    }

    @Test
    @DisplayName("Возврат статуса 404 при обновлении по не существующему ID")
    public void putPet_NotFound()
    {
        RestAssured.given().
                contentType(ContentType.JSON).
                body(new Pet().id(0L).name("Kochka")).
                put("/pet").then().statusCode(404);
    }
}
