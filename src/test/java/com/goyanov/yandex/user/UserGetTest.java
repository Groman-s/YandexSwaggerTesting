package com.goyanov.yandex.user;

import com.goyanov.yandex.swagger.openapi.testing.api.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserGetTest
{
    @Autowired
    private UserApi userApi;
}
