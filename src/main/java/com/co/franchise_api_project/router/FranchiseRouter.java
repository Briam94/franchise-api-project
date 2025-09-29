package com.co.franchise_api_project.router;

import com.co.franchise_api_project.handler.ApiHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FranchiseRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/franchises",
                    beanClass = ApiHandler.class,
                    beanMethod = "createFranchise"
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/name",
                    beanClass = ApiHandler.class,
                    beanMethod = "updateFranchiseName"
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches",
                    beanClass = ApiHandler.class,
                    beanMethod = "addBranch"
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/name",
                    beanClass = ApiHandler.class,
                    beanMethod = "updateBranchName"
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products",
                    beanClass = ApiHandler.class,
                    beanMethod = "addProduct"
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}",
                    beanClass = ApiHandler.class,
                    beanMethod = "deleteProduct"
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock",
                    beanClass = ApiHandler.class,
                    beanMethod = "updateProductStock"
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/name",
                    beanClass = ApiHandler.class,
                    beanMethod = "updateProductName"
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/max-stock",
                    beanClass = ApiHandler.class,
                    beanMethod = "getMaxStock"
            )
    })
    public RouterFunction<ServerResponse> routes(ApiHandler h) {
        return RouterFunctions.route()
                .POST("/api/franchises", h::createFranchise)
                .PUT ("/api/franchises/{franchiseId}/name", h::updateFranchiseName)
                .POST("/api/franchises/{franchiseId}/branches", h::addBranch)
                .PUT ("/api/franchises/{franchiseId}/branches/{branchId}/name", h::updateBranchName)
                .POST("/api/franchises/{franchiseId}/branches/{branchId}/products", h::addProduct)
                .DELETE("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}", h::deleteProduct)
                .PATCH("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock", h::updateProductStock)
                .PUT ("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/name", h::updateProductName)
                .GET ("/api/franchises/{franchiseId}/max-stock", h::getMaxStock)
                .build();
    }
}
