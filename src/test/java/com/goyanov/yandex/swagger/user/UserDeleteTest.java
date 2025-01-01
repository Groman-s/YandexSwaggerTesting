package com.goyanov.yandex.swagger.user;

import com.goyanov.yandex.swagger.openapi.testing.api.UserApi;
import com.goyanov.yandex.swagger.openapi.testing.model.Pet;
import com.goyanov.yandex.swagger.openapi.testing.model.User;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Delete методы пользователей (Swagger)")
public class UserDeleteTest
{
    @Autowired
    private UserApi userApi;

    @Step("Шаг 1 (добавление пользователя)")
    private void addUser(Long userId, String userName)
    {
        userApi.createUser(new User().id(userId).username(userName));
    }

    @Step("Шаг 2 (удаление пользователя)")
    public void removeUser(String userName)
    {
        userApi.deleteUser(userName);
    }

    @Test
    @DisplayName("Успешное удаление пользователя")
    public void deleteUserByUserName_Successful()
    {
        addUser(43L, "leha");
        removeUser("leha");
    }

    @Test
    @DisplayName("Возврат статуса 400 при удалении пользователя с указанием невалидного юзернейма")
    public void deleteUserByUserName_InvalidUsername()
    {
        assertThrows(HttpClientErrorException.class, () -> userApi.deleteUser(null));
    }

    @Test
    @DisplayName("Возврат статуса 404 при удалении пользователя с указанием не существующего юзернейма")
    public void deleteUserByUserName_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> userApi.deleteUser("7ayDW&A &AWDjhi (AP(iuh a"));
    }
}
