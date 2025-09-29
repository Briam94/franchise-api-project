package com.co.franchise_api_project.adapter.out.persistence.mongodb.repository;

import com.co.franchise_api_project.adapter.out.persistence.mongodb.entity.FranchiseEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranchiseMongoRepository extends ReactiveMongoRepository<FranchiseEntity, String> {
}
