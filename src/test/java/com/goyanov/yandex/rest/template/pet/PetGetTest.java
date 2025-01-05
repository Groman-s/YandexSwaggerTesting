package com.goyanov.yandex.rest.template.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Category;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Get методы питомцев (RestTemplate)")
public class PetGetTest
{
    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "https://petstore.swagger.io/v2";

    @Step("Шаг 1 (добавление питомца)")
    public void addPet(Long id)
    {
        restTemplate.postForObject(BASE_URL + "/pet", new Pet().id(id), Pet.class);
    }

    @Step("Шаг 2 (получение питомца)")
    public void getPetWithStatus200(Long id)
    {
        ResponseEntity<Pet> response = restTemplate.getForEntity(BASE_URL + "/pet/" + id.intValue(), Pet.class);
        assertEquals(200, response.getStatusCodeValue());
        Pet pet = response.getBody();
        assertNotNull(pet);
        assertEquals(id, pet.getId());
    }

    @Test
    @DisplayName("Успешное получение питомца по ID")
    public void getPetById_Successful()
    {
        addPet(502L);
        getPetWithStatus200(502L);
    }

    @Test
    @DisplayName("Возврат статуса 400 при получении питомца с указанием невалидного ID")
    public void getPetById_InvalidId()
    {
        assertThrows(HttpClientErrorException.class, () ->
        {
            restTemplate.getForEntity(BASE_URL + "/pet/-234798yhiad", Pet.class);
        });
    }

    @Test
    @DisplayName("Возврат статуса 404 при получении питомца с указанием не существующего ID")
    public void getPetById_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () ->
        {
            restTemplate.getForEntity(BASE_URL + "/pet/0", Pet.class);
        });
    }
}
