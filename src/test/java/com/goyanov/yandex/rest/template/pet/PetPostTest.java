package com.goyanov.yandex.rest.template.pet;

import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Post методы питомцев (RestTemplate)")
public class PetPostTest
{
    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "https://petstore.swagger.io/v2";

    @Test
    @DisplayName("Успешное добавление питомца")
    public void postPet_Successful()
    {
        ResponseEntity<Pet> response = restTemplate.postForEntity(BASE_URL + "/pet", new Pet().id(2882L).name("Petrushka"), Pet.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Возврат статуса 405 при попытке добавления невалидного питомца")
    public void postPet_InvalidInput()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        assertThrows(HttpClientErrorException.MethodNotAllowed.class, () ->
                restTemplate.exchange(BASE_URL + "/pet", HttpMethod.POST, new HttpEntity<Pet>(null, headers), Pet.class)
        );
    }
}
