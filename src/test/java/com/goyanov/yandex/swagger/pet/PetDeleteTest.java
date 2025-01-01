package com.goyanov.yandex.swagger.pet;

import com.goyanov.yandex.swagger.openapi.testing.api.PetApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Delete методы питомцев (Swagger)")
public class PetDeleteTest
{
    @Autowired
    private PetApi petApi;

    @Step("Шаг 1 (добавление питомца)")
    private void addPet(Long petId)
    {
        petApi.addPet(new Pet().id(petId).name("Parrot"));
    }

    @Step("Шаг 2 (удаление питомца)")
    public void removePet(Long petId)
    {
        petApi.deletePet(petId, null);
    }

    @Test
    @DisplayName("Успешное удаление питомца по ID")
    public void deletePetById_Successful()
    {
        addPet(3443L);
        removePet(3443L);
    }

    @Test
    @DisplayName("Возврат статуса 400 при удалении питомца с указанием невалидного ID")
    public void deletePetById_InvalidId()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.deletePet(null, null));
    }

    @Test
    @DisplayName("Возврат статуса 404 при удалении питомца с указанием не существующего ID")
    public void deletePetById_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> petApi.deletePet(0L, null));
    }
}
