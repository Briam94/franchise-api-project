package com.co.franchise_api_project.usecase;

import com.co.franchise_api_project.domain.exception.NotFoundException;
import com.co.franchise_api_project.domain.model.Franchise;
import com.co.franchise_api_project.domain.port.FranchiseRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class FranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> createFranchise(String name) {
        if (name.isBlank()) return Mono.error(new IllegalArgumentException("Name required"));
        return franchiseRepository.save(new Franchise(name));
    }

    public Mono<Franchise> updateFranchiseName(String id, String newName) {
        return franchiseRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMap(f -> franchiseRepository.save(new Franchise(f.id(), newName, f.branches())));
    }
}
