package com.goyanov.yandex.swagger.user;

import com.goyanov.yandex.swagger.openapi.testing.api.UserApi;
import com.goyanov.yandex.swagger.openapi.testing.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Post методы пользователей (Swagger)")
public class UserPostTest
{
    @Autowired
    private UserApi userApi;

    @Test
    @DisplayName("Успешное создание нового пользователя")
    public void userPost_Successful()
    {
        userApi.createUser(new User().
                id(1L).username("user").
                firstName("oleg").lastName("egorov").email("dawd@yandex.ru").
                password("pass123").phone("+733993").userStatus(3)
        );
    }

    @Test
    @DisplayName("Успешное создание пользователей (массив)")
    public void userPost_SuccessfulCreatingWithArray()
    {
        userApi.createUsersWithArrayInput(List.of(new User().id(1L), new User().id(2L), new User().id(3L)));
    }

    @Test
    @DisplayName("Успешное создание пользователей (список)")
    public void userPost_SuccessfulCreatingWithList()
    {
        userApi.createUsersWithListInput(List.of(new User().id(1L), new User().id(2L)));
    }
}
