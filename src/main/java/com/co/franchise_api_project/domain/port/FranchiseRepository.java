package com.co.franchise_api_project.domain.port;

import com.co.franchise_api_project.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(String id);
}
