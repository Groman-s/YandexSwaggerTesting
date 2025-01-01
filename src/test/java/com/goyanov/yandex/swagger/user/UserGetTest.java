package com.goyanov.yandex.swagger.user;

import com.goyanov.yandex.swagger.openapi.testing.api.UserApi;
import com.goyanov.yandex.swagger.openapi.testing.model.User;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Get методы пользователей (Swagger)")
public class UserGetTest
{
    @Autowired
    private UserApi userApi;

    @Step("Шаг 1 (добавление пользователя)")
    private void addUser(Long userId, String userName)
    {
        userApi.createUser(new User().id(userId).username(userName));
    }

    @Step("Шаг 2 (получение пользователя)")
    public User getUser(String userName)
    {
        return userApi.getUserByName(userName);
    }

    @Test
    @DisplayName("Успешное получение пользователя")
    public void userGetByUsername_Successful()
    {
        addUser(1L, "user");
        assertEquals(1L, getUser("user").getId());
    }

    @Test
    @DisplayName("Возврат статуса 400 при получении пользователя с указанием невалидного юзернейма")
    public void userGetByUsername_InvalidUsername()
    {
        assertThrows(HttpClientErrorException.class, () -> userApi.getUserByName(null));
    }

    @Test
    @DisplayName("Возврат статуса 404 при получении пользователя с указанием не существующего юзернейма")
    public void userGetByUsername_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> userApi.getUserByName("adwd98u8ah7DFA#"));
    }

    @Test
    @DisplayName("Успешная аутентификация пользователя")
    public void userGetDoLogin_SuccessfulLogin()
    {
        userApi.loginUser("user34", "password123");
    }

    @Test
    @DisplayName("Возврат статуса 400 при попытке аутентификации пользователя с неверными данными")
    public void userGetDoLogin_InvalidPassword()
    {
        assertThrows(HttpClientErrorException.class, () -> userApi.loginUser("user34", null));
    }

    @Test
    @DisplayName("Успешный выход из аккаунта пользователя")
    public void userGetDoLogout_SuccessfulLogout()
    {
        userApi.logoutUser();
    }
}
