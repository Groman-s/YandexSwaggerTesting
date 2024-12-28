package com.goyanov.yandex.pet;

import com.goyanov.yandex.swagger.openapi.testing.api.PetApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
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

    @Test
    public void getPet_CheckSearchByStatusWorks()
    {
        List<Pet> available = petApi.findPetsByStatus(List.of("available"));
        assertTrue(available.stream().allMatch(pet -> pet.getStatus() == Pet.StatusEnum.AVAILABLE));

        List<Pet> pending = petApi.findPetsByStatus(List.of("pending"));
        assertTrue(pending.stream().allMatch(pet -> pet.getStatus() == Pet.StatusEnum.PENDING));

        List<Pet> sold = petApi.findPetsByStatus(List.of("sold"));
        assertTrue(sold.stream().allMatch(pet -> pet.getStatus() == Pet.StatusEnum.SOLD));
    }

    @Test
    public void getPet_InvalidStatus()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.findPetsByStatus(null));
    }
}