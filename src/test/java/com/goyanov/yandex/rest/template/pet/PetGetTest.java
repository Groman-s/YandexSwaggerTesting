package com.goyanov.yandex.rest.template.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Category;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Get методы питомцев (RestTemplate)")
public class PetGetTest
{
    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "https://petstore.swagger.io/v2";

    @Test
    @DisplayName("Успешное получение питомца по ID")
    public void getPetById_Successful()
    {
        Pet pet = new Pet().id(500L).name("Leika").category(new Category().id(3L).name("Pes"));
        restTemplate.postForObject(BASE_URL + "/pet", pet, Pet.class);
        ResponseEntity<Pet> response = restTemplate.getForEntity(BASE_URL + "/pet/500", Pet.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(pet);
        assertEquals(500L, pet.getId());
        assertEquals("Leika", pet.getName());
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
