package com.co.franchise_api_project.usecase;

import com.co.franchise_api_project.domain.exception.NotFoundException;
import com.co.franchise_api_project.domain.model.Branch;
import com.co.franchise_api_project.domain.model.Franchise;
import com.co.franchise_api_project.domain.port.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BranchUseCaseTest {

    @Mock
    FranchiseRepository franchiseRepository;
    BranchUseCase branchUseCase;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        branchUseCase = new BranchUseCase(franchiseRepository);
    }

    @Test
        void addBranch_whenFranchiseNotFound_shouldError_addProduct() {
        Mockito.when(franchiseRepository.findById("F1")).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.addBranch("F1", "B"))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void addBranch_whenFound_shouldReturnNewBranch() {
        Franchise fr = new Franchise("F1", "Name", List.of());
        Mockito.when(franchiseRepository.findById("F1")).thenReturn(Mono.just(fr));
        Mockito.when(franchiseRepository.save(Mockito.any()))
                .thenReturn(Mono.just(fr));

        StepVerifier.create(branchUseCase.addBranch("F1", "NewBranch"))
                .expectNextMatches(b -> b.name().equals("NewBranch"))
                .verifyComplete();
    }

    @Test
    void updateBranchName_whenFranchiseNotFound_shouldError() {
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateBranchName("F", "B", "New"))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void updateBranchName_whenFound_shouldRenameBranch() {
        Branch branch = new Branch("B", "Old", List.of());
        Franchise fr     = new Franchise("F", "Name", List.of(branch));
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.just(fr));
        Mockito.when(franchiseRepository.save(Mockito.any())).thenReturn(Mono.just(fr));

        StepVerifier.create(branchUseCase.updateBranchName("F", "B", "New"))
                .expectNextMatches(b -> b.name().equals("New"))
                .verifyComplete();
    }

}