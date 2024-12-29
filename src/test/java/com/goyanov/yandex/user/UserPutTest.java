package com.goyanov.yandex.user;

import com.goyanov.yandex.swagger.openapi.testing.api.UserApi;
import com.goyanov.yandex.swagger.openapi.testing.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserPutTest
{
    @Autowired
    private UserApi userApi;

    @Test
    public void userPut_SuccessfulUpdate()
    {
        User user = new User().id(3L).username("test-user").lastName("joihbov");
        userApi.createUser(user);
        userApi.updateUser("test-user", user);
    }

    @Test
    public void userPut_InvalidUser()
    {
        assertThrows(HttpClientErrorException.class, () -> userApi.updateUser("user", null));
    }

    @Test
    public void userPut_NotFound()
    {
        assertThrows(HttpClientErrorException.NotFound.class,
                ()-> userApi.updateUser("*AYd9ahuG&*(#I", new User().id(3L).username("test-user").lastName("joihbov")));
    }
}
