package com.co.franchise_api_project.adapter.out.persistence.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class ProductEntity {
    @Id
    private String id;
    private String name;
    private int stock;
}
