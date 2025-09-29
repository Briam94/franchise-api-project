package com.co.franchise_api_project.adapter.out.persistence.mongodb.entity;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchEntity {
    private String id;
    private String name;
    private List<ProductEntity> products;
}
