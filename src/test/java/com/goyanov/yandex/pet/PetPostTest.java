package com.goyanov.yandex.pet;

import com.goyanov.yandex.swagger.openapi.testing.api.PetApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Category;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import com.goyanov.yandex.swagger.openapi.testing.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PetPostTest
{
    @Autowired
    private PetApi petApi;

    @Test
    public void postPet_Successful1()
    {
        petApi.addPet(new Pet());
    }

    @Test
    public void postPet_InvalidInput()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.addPet(null));
    }

    @Test
    public void postPet_Successful2()
    {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Petty");
        pet.setCategory(new Category());
        pet.setTags(List.of(new Tag()));
        pet.setStatus(Pet.StatusEnum.SOLD);
        pet.setPhotoUrls(List.of("petty.jpeg"));

        petApi.addPet(new Pet());
    }

    @Test
    public void postPet_SuccessfulInputUpdatingById()
    {
        petApi.updatePetWithForm(500L, "Leika", "sold");
    }

    @Test
    public void postPet_InvalidInputUpdatingById()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.updatePetWithForm(0L, "hruk", "some-status"));
    }
}