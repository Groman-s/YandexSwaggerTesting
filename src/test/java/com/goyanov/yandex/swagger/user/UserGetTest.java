package com.goyanov.yandex.swagger.user;

import com.goyanov.yandex.swagger.openapi.testing.api.UserApi;
import com.goyanov.yandex.swagger.openapi.testing.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserGetTest
{
    @Autowired
    private UserApi userApi;

    @Test
    public void userGet_Successful()
    {
        userApi.createUser(new User().id(1L).username("user"));
        userApi.getUserByName("user");
    }

    @Test
    public void userGet_InvalidUsername()
    {
        assertThrows(HttpClientErrorException.class, () -> userApi.getUserByName(null));
    }

    @Test
    public void userGet_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> userApi.getUserByName("adwd98u8ah7DFA#"));
    }

    @Test
    public void userGet_SuccessfulLogin()
    {
        userApi.loginUser("user34", "password123");
    }

    @Test
    public void userGet_InvalidPassword()
    {
        assertThrows(HttpClientErrorException.class, () -> userApi.loginUser("user34", null));
    }

    @Test
    public void userGet_SuccessfulLogout()
    {
        userApi.logoutUser();
    }
}
