package com.goyanov.yandex.swagger.store;

import com.goyanov.yandex.swagger.openapi.testing.api.StoreApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Order;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Get методы магазина (Swagger)")
public class StoreGetTest
{
    @Autowired
    private StoreApi storeApi;

    @Test
    @DisplayName("Успешное получение инвентаря магазина")
    public void getStoreInventory_Successful()
    {
        storeApi.getInventory();
    }

    @Step("Шаг 1 (добавление заказа)")
    private void addOrder(Long orderId)
    {
        storeApi.placeOrder(new Order().id(orderId).complete(true).quantity(5));
    }

    @Step("Шаг 2 (получение заказа)")
    public Order getOrder(Long orderId)
    {
        return storeApi.getOrderById(orderId);
    }

    @Test
    @DisplayName("Успешное получение инвентаря магазина")
    public void getStoreOrderById_Successful()
    {
        addOrder(3L);
        assertEquals(3L, getOrder(3L).getId());
    }

    @ParameterizedTest
    @ValueSource(longs = {-3, 0, 11, 20, 60})
    @DisplayName("Возврат статуса 400 при получении заказа с указанием невалидного ID")
    public void getStoreOrderById_InvalidId(Long id)
    {
        assertThrows(HttpClientErrorException.class, () -> storeApi.getOrderById(id));
    }

    @Test
    @DisplayName("Возврат статуса 404 при получении заказа с указанием не существующего ID")
    public void getStoreOrderById_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> storeApi.getOrderById(431273019L));
    }
}
