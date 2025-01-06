package com.goyanov.yandex.swagger.pet;

import com.goyanov.yandex.swagger.openapi.testing.api.PetApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Get методы питомцев (Swagger)")
public class PetGetTest
{
    @Autowired
    private PetApi petApi;

    @Step("Шаг 1 (добавление питомца)")
    private void addPet(Long petId)
    {
        petApi.addPet(new Pet().id(petId).name("Parrot"));
    }

    @Step("Шаг 2 (получение питомца)")
    public Pet getPet(Long petId)
    {
        return petApi.getPetById(petId);
    }

    @Test
    @DisplayName("Успешное получение питомца по ID")
    public void getPetById_Successful()
    {
        addPet(500L);
        assertEquals(500L, getPet(500L).getId());
    }

    @Test
    @DisplayName("Возврат статуса 400 при получении питомца с указанием невалидного ID")
    public void getPetById_InvalidId()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.getPetById(null));
    }

    @Test
    @DisplayName("Возврат статуса 404 при получении питомца с указанием не существующего ID")
    public void getPetById_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> petApi.getPetById(0L));
    }

    @ParameterizedTest
    @EnumSource(Pet.StatusEnum.class)
    @DisplayName("Успешное получение питомцев по указанному статусу")
    public void getPetByStatus_Successful(Pet.StatusEnum status)
    {
        List<Pet> available = petApi.findPetsByStatus(List.of(status.getValue()));
        assertTrue(available.stream().allMatch(pet -> pet.getStatus() == status));
    }

    @Test
    @DisplayName("Успешное получение питомцев по указанным статусам")
    public void getPetByMultipleStatuses_Successful()
    {
        List<Pet> available = petApi.findPetsByStatus(
                List.of(Pet.StatusEnum.AVAILABLE.getValue(), Pet.StatusEnum.SOLD.getValue())
        );
        assertTrue(available.stream().noneMatch(pet -> pet.getStatus() == Pet.StatusEnum.PENDING));
    }

    @Test
    @DisplayName("Возврат статуса 400 при попытке получить питомца с указанием невалидного статуса")
    public void getPetByStatus_InvalidStatus()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.findPetsByStatus(null));
    }
}