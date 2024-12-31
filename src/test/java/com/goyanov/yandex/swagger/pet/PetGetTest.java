package com.goyanov.yandex.swagger.pet;

import com.goyanov.yandex.swagger.openapi.testing.api.PetApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetGetTest
{
    @Autowired
    private PetApi petApi;

    @Test
    public void getPet_Found()
    {
        assertEquals(500L, petApi.getPetById(500L).getId());
    }

    @Test
    public void getPet_InvalidId()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.getPetById(null));
    }

    @Test
    public void getPet_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> petApi.getPetById(0L));
    }

    @ParameterizedTest
    @EnumSource(Pet.StatusEnum.class)
    public void getPet_CheckSearchByStatusWorks(Pet.StatusEnum status)
    {
        List<Pet> available = petApi.findPetsByStatus(List.of(status.getValue()));
        assertTrue(available.stream().allMatch(pet -> pet.getStatus() == status));
    }

    @Test
    public void getPet_InvalidStatus()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.findPetsByStatus(null));
    }
}