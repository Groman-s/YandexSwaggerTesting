package com.goyanov.yandex.swagger.pet;

import com.goyanov.yandex.swagger.openapi.testing.api.PetApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PetPutTest
{
    @Autowired
    private PetApi petApi;

    @Test
    public void putPet_Successful()
    {
        petApi.updatePet(new Pet().id(500L).name("Lozhka"));
    }

    @Test
    public void putPet_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> petApi.updatePet(new Pet().id(0L)));
    }

    @Test
    public void putPet_InvalidId()
    {
        assertThrows(HttpClientErrorException.class, () -> petApi.updatePet(new Pet().id(null)));
    }
}
