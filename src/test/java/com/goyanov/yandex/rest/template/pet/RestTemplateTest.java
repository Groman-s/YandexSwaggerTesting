package com.goyanov.yandex.rest.template.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public abstract class RestTemplateTest
{
    @Autowired
    protected RestTemplate restTemplate;

    protected final String BASE_URL = "https://petstore.swagger.io/v2";
}
