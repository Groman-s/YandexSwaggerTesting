package com.goyanov.yandex.swagger.pet;

import com.goyanov.yandex.swagger.openapi.testing.api.PetApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Category;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import com.goyanov.yandex.swagger.openapi.testing.model.Tag;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Post методы питомцев (Swagger)")
public class PetPostTest
{
    @Autowired
    private PetApi petApi;

    private static Stream<Pet> providedAddedPets()
    {
        return Stream.of
        (
            new Pet(),
            new Pet().id(1L).name("Petty").category(new Category().id(1L)).status(Pet.StatusEnum.SOLD)
        );
    }

    @ParameterizedTest
    @MethodSource("providedAddedPets")
    @DisplayName("Успешное добавление питомца")
    public void postPet_Successful(Pet pet)
    {
        petApi.addPet(pet);
    }

    @Test
    @DisplayName("Возврат статуса 400 при попытке добавления невалидных данных")
    public void postPet_InvalidInput()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.addPet(null));
    }

    @Step("Шаг 1 (добавление питомца)")
    private void addPet(Long petId)
    {
        petApi.addPet(new Pet().id(petId));
    }

    @Step("Шаг 2 (обновление питомца)")
    public void updatePet(Long petId, String name, String status)
    {
        petApi.updatePetWithForm(petId, name, status);
    }

    @Test
    @DisplayName("Успешное обновление существующего питомца")
    public void postPetUpdateById_Successful()
    {
        addPet(499L);
        updatePet(499L, "Leika", "sold");
    }

    @Test
    @DisplayName("Возврат статуса 405 при обновлении питомца с указанием невалидных данных")
    public void postPetUpdateById_InvalidInput()
    {
        assertThrows(HttpClientErrorException.MethodNotAllowed.class, () -> petApi.updatePetWithForm(null, "hruk", "some-status"));
    }
}