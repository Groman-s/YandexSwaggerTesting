package com.goyanov.yandex.swagger.store;

import com.goyanov.yandex.swagger.openapi.testing.api.StoreApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Post методы магазина (Swagger)")
public class StorePostTest
{
    @Autowired
    private StoreApi storeApi;

    @Test
    public void postStoreOrder_Successful()
    {
        storeApi.placeOrder(new Order().id(3L).complete(true).quantity(5).petId(6L).status(Order.StatusEnum.PLACED));
    }

    @Test
    public void postStoreOrder_InvalidOrder()
    {
        assertThrows(HttpClientErrorException.class, () -> storeApi.placeOrder(null));
    }
}
