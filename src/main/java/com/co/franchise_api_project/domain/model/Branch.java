package com.co.franchise_api_project.domain.model;

import java.util.List;
import java.util.UUID;

public record Branch(String id, String name, List<Product> products) {
    public Branch(String name) {
        this(UUID.randomUUID().toString(), name, List.of());
    }
}
