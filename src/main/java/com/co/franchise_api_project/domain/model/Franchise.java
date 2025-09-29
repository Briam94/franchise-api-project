package com.co.franchise_api_project.domain.model;

import java.util.List;
import java.util.UUID;

public record Franchise(String id, String name, List<Branch> branches) {
    public Franchise(String name) {
        this(UUID.randomUUID().toString(), name, List.of());
    }
}