package com.goyanov.yandex.rest.assured.pet;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public abstract class AssuredTest
{
    @BeforeEach
    public void initPath()
    {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
}
