package com.goyanov.yandex.swagger.store;

import com.goyanov.yandex.swagger.openapi.testing.api.StoreApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Order;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Delete методы магазина (Swagger)")
public class StoreDeleteTest
{
    @Autowired
    private StoreApi storeApi;

    @Step("Шаг 1 (добавление заказа)")
    private void addOrder(Long orderId)
    {
        storeApi.placeOrder(new Order().id(orderId).complete(true).quantity(5));
    }

    @Step("Шаг 2 (удаление заказа)")
    public void deleteOrder(Long orderId)
    {
        storeApi.deleteOrder(orderId);
    }

    @Test
    @DisplayName("Успешное удаление заказа")
    public void deleteStoreOrder_Successful()
    {
        addOrder(4L);
        deleteOrder(4L);
    }

    @Test
    @DisplayName("Возврат статуса 400 при удалении заказа с указанием невалидного ID")
    public void deleteStoreOrder_InvalidId()
    {
        assertThrows(HttpClientErrorException.class, () -> storeApi.deleteOrder(null));
    }

    @Test
    @DisplayName("Возврат статуса 404 при удалении заказа с указанием не существующего ID")
    public void deleteStoreOrder_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> storeApi.deleteOrder(-1882L));
    }
}
