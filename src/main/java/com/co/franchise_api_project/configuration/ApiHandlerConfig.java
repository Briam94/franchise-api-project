package com.co.franchise_api_project.configuration;

import com.co.franchise_api_project.handler.ApiHandler;
import com.co.franchise_api_project.usecase.BranchUseCase;
import com.co.franchise_api_project.usecase.FranchiseUseCase;
import com.co.franchise_api_project.usecase.ProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiHandlerConfig {


    private FranchiseUseCase franchiseUseCase;
    private BranchUseCase branchUseCase;
    private ProductUseCase productUseCase;

    @Bean
    public ApiHandler apiHandler() {
        return new ApiHandler(franchiseUseCase, branchUseCase, productUseCase);
    }
}
