package com.co.franchise_api_project.domain.model;

import java.util.UUID;

public record Product(String id, String name, int stock) {
    public Product(String name, int stock) {
        this(UUID.randomUUID().toString(), name, stock);
    }
}