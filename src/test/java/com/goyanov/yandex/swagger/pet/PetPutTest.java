package com.goyanov.yandex.swagger.pet;

import com.goyanov.yandex.swagger.openapi.testing.api.PetApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Put методы питомцев (Swagger)")
public class PetPutTest
{
    @Autowired
    private PetApi petApi;

    @Step("Шаг 1 (добавление питомца)")
    private void addPet(Long petId)
    {
        petApi.addPet(new Pet().id(petId).name("Parrot"));
    }

    @Step("Шаг 2 (обновление питомца)")
    public void updatePet(Pet pet)
    {
        petApi.updatePet(pet);
    }

    @Test
    @DisplayName("Успешное обновление питомца")
    public void putPet_Successful()
    {
        addPet(57L);
        updatePet(new Pet().id(57L).name("Lozhka"));
    }

    @Test
    @DisplayName("Возврат статуса 404 при попытке обновления не существующего питомца")
    public void putPet_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> petApi.updatePet(new Pet().id(0L)));
    }

    @Test
    @DisplayName("Возврат статуса 400 при попытке обновления питомца по невалидному ID")
    public void putPet_InvalidId()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.updatePet(new Pet().id(null)));
    }
}
