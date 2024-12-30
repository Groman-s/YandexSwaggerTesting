package com.goyanov.yandex.swagger.configuration;

import com.goyanov.yandex.swagger.openapi.testing.api.UserApi;
import com.goyanov.yandex.swagger.openapi.testing.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig
{
    private final ApiClient apiClient;

    @Autowired
    public UserConfig(ApiClient apiClient)
    {
        this.apiClient = apiClient;
    }

    @Bean
    public UserApi userApi()
    {
        return new UserApi(apiClient);
    }
}
