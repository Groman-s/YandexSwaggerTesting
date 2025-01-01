package com.goyanov.yandex.swagger.user;

import com.goyanov.yandex.swagger.openapi.testing.api.UserApi;
import com.goyanov.yandex.swagger.openapi.testing.model.User;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Put методы пользователей (Swagger)")
public class UserPutTest
{
    @Autowired
    private UserApi userApi;

    @Step("Шаг 1 (добавление пользователя)")
    private void addUser(User user)
    {
        userApi.createUser(user);
    }

    @Step("Шаг 2 (обновление пользователя)")
    public void updateUser(String userName, User user)
    {
        userApi.updateUser(userName, user);
    }

    @Test
    @DisplayName("Успешное обновление пользователя")
    public void userPut_Successful()
    {
        addUser(new User().id(3L).username("test-user").lastName("joihbov"));
        updateUser("test-user", new User().id(3L).username("second-user").lastName("aduwdh"));
    }

    @Test
    @DisplayName("Возврат статуса 400 при обновлении пользователя с указанием невалидного тела")
    public void userPut_InvalidUser()
    {
        assertThrows(HttpClientErrorException.class, () -> userApi.updateUser("user", null));
    }

    @Test
    @DisplayName("Возврат статуса 404 при обновлении пользователя с указанием не существующего юзернейма")
    public void userPut_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class,
                ()-> userApi.updateUser("*AYd9ahuG&*(#I", new User().id(3L).username("test-user").lastName("joihbov")));
    }
}
