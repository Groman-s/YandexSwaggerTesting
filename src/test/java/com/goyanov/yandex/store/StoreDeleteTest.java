package com.goyanov.yandex.store;

import com.goyanov.yandex.swagger.openapi.testing.api.StoreApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StoreDeleteTest
{
    @Autowired
    private StoreApi storeApi;

    @Test
    public void deleteStoreOrder_Successful()
    {
        storeApi.placeOrder(new Order().id(4L));
        storeApi.deleteOrder(4L);
    }

    @Test
    public void deleteStoreOrder_InvalidId()
    {
        assertThrows(HttpClientErrorException.class, () -> storeApi.deleteOrder(null));
    }

    @Test
    public void deleteStoreOrder_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> storeApi.deleteOrder(-1882L));
    }
}
