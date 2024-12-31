package com.goyanov.yandex.configuration;

import com.goyanov.yandex.swagger.openapi.testing.invoker.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiClientConfig
{
    @Bean
    public ApiClient apiClient()
    {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("https://petstore.swagger.io/v2/");
//        OAuth petStoreAuth = (OAuth) apiClient.getAuthentication("petstore_auth");
//        petStoreAuth.setAccessToken("roman-testing_yandex_547438");
        return apiClient;
    }
}
