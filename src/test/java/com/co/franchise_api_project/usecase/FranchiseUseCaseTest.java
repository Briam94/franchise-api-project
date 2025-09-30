package com.co.franchise_api_project.usecase;

import com.co.franchise_api_project.domain.exception.NotFoundException;
import com.co.franchise_api_project.domain.model.Franchise;
import com.co.franchise_api_project.domain.port.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class FranchiseUseCaseTest {

    @Mock
    FranchiseRepository franchiseRepository;
    @InjectMocks
    FranchiseUseCase franchiseUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franchiseUseCase = new FranchiseUseCase(franchiseRepository);
    }

    @Test
    void createFranchise_whenNameBlank_shouldError() {
        StepVerifier.create(franchiseUseCase.createFranchise(""))
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    void createFranchise_whenValid_shouldSaveAndReturn() {
        String name = "MyFran";
        Franchise saved = new Franchise(name);
        Mockito.when(franchiseRepository.save(Mockito.any())).thenReturn(Mono.just(saved));

        StepVerifier.create(franchiseUseCase.createFranchise(name))
                .expectNextMatches(fr -> fr.name().equals(name))
                .verifyComplete();

        Mockito.verify(franchiseRepository).save(Mockito.any());
    }

    @Test
    void createFranchise_whenNotFound_shouldError() {
        Mockito.when(franchiseRepository.findById("X")).thenReturn(Mono.empty());

        StepVerifier.create(franchiseUseCase.updateFranchiseName("X", "New"))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void createFranchise_whenFound_shouldUpdateName() {
        Franchise original = new Franchise("id1", "Old", List.of());
        Franchise updated  = new Franchise("id1", "New", List.of());
        Mockito.when(franchiseRepository.findById("id1")).thenReturn(Mono.just(original));
        Mockito.when(franchiseRepository.save(Mockito.any())).thenReturn(Mono.just(updated));

        StepVerifier.create(franchiseUseCase.updateFranchiseName("id1", "New"))
                .expectNextMatches(fr -> fr.name().equals("New"))
                .verifyComplete();
    }

}