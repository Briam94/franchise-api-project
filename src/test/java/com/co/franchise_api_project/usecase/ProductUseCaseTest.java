package com.co.franchise_api_project.usecase;

import com.co.franchise_api_project.domain.exception.NotFoundException;
import com.co.franchise_api_project.domain.model.Branch;
import com.co.franchise_api_project.domain.model.Franchise;
import com.co.franchise_api_project.domain.model.Product;
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

class ProductUseCaseTest {

    @Mock
    FranchiseRepository franchiseRepository;
    ProductUseCase productUseCase;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        productUseCase = new ProductUseCase(franchiseRepository);
    }

    @Test
    void addProduct_whenFranchiseNotFound_shouldError() {
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.addProduct("F","B","P",5))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void addProduct_whenFound_shouldReturnNewProduct() {
        Branch branch = new Branch("B","Name", List.of());
        Franchise fr = new Franchise("F","N", List.of(branch));
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.just(fr));
        Mockito.when(franchiseRepository.save(Mockito.any())).thenReturn(Mono.just(fr));

        StepVerifier.create(productUseCase.addProduct("F","B","P",5))
                .expectNextMatches(p -> p.name().equals("P") && p.stock()==5)
                .verifyComplete();
    }

    @Test
    void deleteProduct_whenFranchiseNotFound_shouldError() {
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.deleteProduct("F","B","P"))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void deleteProduct_whenFound_shouldComplete() {
        Product prod   = new Product("P","N",1);
        Branch branch = new Branch("B","NB", List.of(prod));
        Franchise fr     = new Franchise("F","N", List.of(branch));
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.just(fr));
        Mockito.when(franchiseRepository.save(Mockito.any())).thenReturn(Mono.just(fr));

        StepVerifier.create(productUseCase.deleteProduct("F","B","P"))
                .verifyComplete();
    }

    @Test
    void updateProductStock_whenFranchiseNotFound_shouldError() {
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateProductStock("F","B","P",10))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void updateProductStock_whenFound_shouldReturnUpdatedProduct() {
        Product prod   = new Product("P","N",1);
        Branch branch = new Branch("B","NB", List.of(prod));
        Franchise fr     = new Franchise("F","N", List.of(branch));
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.just(fr));
        Mockito.when(franchiseRepository.save(Mockito.any())).thenReturn(Mono.just(fr));

        StepVerifier.create(productUseCase.updateProductStock("F","B","P",10))
                .expectNextMatches(p -> p.stock()==10)
                .verifyComplete();
    }

    @Test
    void updateProductName_whenFranchiseNotFound_shouldError() {
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateProductName("F","B","P","New"))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void updateProductName_whenFound_shouldReturnRenamedProduct() {
        Product prod   = new Product("P","Old",1);
        Branch branch = new Branch("B","NB", List.of(prod));
        Franchise fr     = new Franchise("F","N", List.of(branch));
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.just(fr));
        Mockito.when(franchiseRepository.save(Mockito.any())).thenReturn(Mono.just(fr));

        StepVerifier.create(productUseCase.updateProductName("F","B","P","New"))
                .expectNextMatches(p -> p.name().equals("New"))
                .verifyComplete();
    }

    @Test
    void maxStockByBranch_whenFranchiseNotFound_shouldError() {
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.maxStockByBranch("F"))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void maxStockByBranch_whenNoProducts_shouldError() {
        Branch branch = new Branch("B","NB", List.of());
        Franchise fr     = new Franchise("F","N", List.of(branch));
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.just(fr));

        StepVerifier.create(productUseCase.maxStockByBranch("F"))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void maxStockByBranch_whenHasProducts_shouldEmitOnePerBranch() {
        Product p1 = new Product("P1","A",2);
        Product p2 = new Product("P2","B",5);
        Branch b1 = new Branch("B1","NB", List.of(p1, p2));
        Branch b2 = new Branch("B2","NB", List.of(p1));
        Franchise fr = new Franchise("F","N", List.of(b1, b2));
        Mockito.when(franchiseRepository.findById("F")).thenReturn(Mono.just(fr));

        StepVerifier.create(productUseCase.maxStockByBranch("F"))
                .expectNextCount(2)
                .verifyComplete();
    }

}