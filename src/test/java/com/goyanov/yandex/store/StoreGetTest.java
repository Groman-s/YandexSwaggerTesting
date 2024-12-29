package com.goyanov.yandex.store;

import com.goyanov.yandex.swagger.openapi.testing.api.StoreApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StoreGetTest
{
    @Autowired
    private StoreApi storeApi;

    @Test
    public void getStoreInventory_Successful()
    {
        storeApi.getInventory();
    }

    @Test
    public void getStoreOrder_Successful()
    {
        storeApi.placeOrder(new Order().id(3L).complete(true).quantity(5));
        storeApi.getOrderById(3L);
    }

    @Test
    public void getStoreOrder_InvalidId()
    {
        assertThrows(HttpClientErrorException.class, () -> storeApi.getOrderById(null));
    }

    @Test
    public void getStoreOrder_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> storeApi.getOrderById(439L));
    }
}
