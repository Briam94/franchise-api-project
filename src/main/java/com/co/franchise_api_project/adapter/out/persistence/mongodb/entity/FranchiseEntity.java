package com.co.franchise_api_project.adapter.out.persistence.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@Document("franchises")
public class FranchiseEntity {
    @Id
    private String id;
    private String name;
    private List<BranchEntity> branches;
}
