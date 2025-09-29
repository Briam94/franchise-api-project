package com.co.franchise_api_project.configuration;

import com.co.franchise_api_project.handler.ApiHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiHandlerConfig {

    @Bean
    public ApiHandler apiHandler() {
        return new ApiHandler();
    }
}
