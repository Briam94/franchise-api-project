package com.co.franchise_api_project.usecase;

import com.co.franchise_api_project.domain.exception.NotFoundException;
import com.co.franchise_api_project.domain.model.Branch;
import com.co.franchise_api_project.domain.model.Franchise;
import com.co.franchise_api_project.domain.port.FranchiseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class BranchUseCase {

    private final FranchiseRepository franchiseRepository;

    public Mono<Branch> addBranch(String franchiseId, String branchName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMap(fr -> {
                    var branch = new Branch(branchName);
                    var updated = new Franchise(fr.id(), fr.name(),
                            Stream.concat(fr.branches().stream(), Stream.of(branch))
                                    .toList());
                    return franchiseRepository.save(updated).thenReturn(branch);
                });
    }

    public Mono<Branch> updateBranchName(String franchiseId, String branchId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMap(fr -> {
                    var branches = fr.branches().stream()
                            .map(b -> b.id().equals(branchId)
                                    ? new Branch(branchId, newName, b.products())
                                    : b)
                            .toList();
                    var updated = new Franchise(fr.id(), fr.name(), branches);
                    return franchiseRepository.save(updated)
                            .thenReturn(branches.stream()
                                    .filter(b -> b.id().equals(branchId))
                                    .findFirst()
                                    .orElseThrow(() -> new NotFoundException("Branch not found")));
                });
    }
}
