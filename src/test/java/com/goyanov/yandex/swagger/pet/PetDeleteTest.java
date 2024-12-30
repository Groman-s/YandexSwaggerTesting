package com.goyanov.yandex.swagger.pet;

import com.goyanov.yandex.swagger.openapi.testing.api.PetApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PetDeleteTest
{
    @Autowired
    private PetApi petApi;

    @Test
    public void deletePet_Successful()
    {
        petApi.addPet(new Pet().id(3443L).name("Parrot"));
        petApi.deletePet(3443L, null);
    }

    @Test
    public void deletePet_InvalidId()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.deletePet(null, null));
    }

    @Test
    public void deletePet_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> petApi.deletePet(0L, null));
    }
}
