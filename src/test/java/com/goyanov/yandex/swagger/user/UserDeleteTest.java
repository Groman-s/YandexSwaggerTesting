package com.goyanov.yandex.swagger.user;

import com.goyanov.yandex.swagger.openapi.testing.api.UserApi;
import com.goyanov.yandex.swagger.openapi.testing.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDeleteTest
{
    @Autowired
    private UserApi userApi;

    @Test
    public void deleteUser_Successful()
    {
        userApi.createUser(new User().id(34L).username("leha"));
        userApi.deleteUser("leha");
    }

    @Test
    public void deleteUser_InvalidUsername()
    {
        assertThrows(HttpClientErrorException.class, () -> userApi.deleteUser(null));
    }

    @Test
    public void deleteUser_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class, () -> userApi.deleteUser("7ayDW&A &AWDjhi (AP(iuh a"));
    }
}
