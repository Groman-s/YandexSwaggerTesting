package com.goyanov.yandex.configuration;

import com.goyanov.yandex.swagger.openapi.testing.api.PetApi;
import com.goyanov.yandex.swagger.openapi.testing.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetConfig
{
    private final ApiClient apiClient;

    @Autowired
    public PetConfig(ApiClient apiClient)
    {
        this.apiClient = apiClient;
    }

    @Bean
    public PetApi petApi()
    {
        return new PetApi(apiClient);
    }
}
