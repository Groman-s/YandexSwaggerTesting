package com.goyanov.yandex.configuration;

import com.goyanov.yandex.swagger.openapi.testing.api.StoreApi;
import com.goyanov.yandex.swagger.openapi.testing.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StoreConfig
{
    private final ApiClient apiClient;

    @Autowired
    public StoreConfig(ApiClient apiClient)
    {
        this.apiClient = apiClient;
    }

    @Bean
    public StoreApi storeApi()
    {
        return new StoreApi(apiClient);
    }
}
