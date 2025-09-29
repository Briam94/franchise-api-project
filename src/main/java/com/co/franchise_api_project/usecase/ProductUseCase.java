package com.co.franchise_api_project.usecase;

import com.co.franchise_api_project.domain.exception.NotFoundException;
import com.co.franchise_api_project.domain.model.Branch;
import com.co.franchise_api_project.domain.model.Franchise;
import com.co.franchise_api_project.domain.model.Product;
import com.co.franchise_api_project.domain.port.FranchiseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class ProductUseCase {

    private final FranchiseRepository franchiseRepository;

    public Mono<Product> addProduct(String franchiseId, String branchId,
                                        String name, int stock) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMap(fr -> {
                    var branches = fr.branches().stream().map(b -> {
                        if (!b.id().equals(branchId)) return b;
                        var p = new Product(name, stock);
                        return new Branch(b.id(), b.name(),
                                Stream.concat(b.products().stream(), Stream.of(p))
                                        .toList());
                    }).toList();
                    var updated = new Franchise(fr.id(), fr.name(), branches);
                    return franchiseRepository.save(updated)
                            .thenReturn(branches.stream()
                                    .flatMap(b -> b.products().stream())
                                    .filter(p -> p.name().equals(name))
                                    .findFirst()
                                    .orElseThrow());
                });
    }

    public Mono<Void> deleteProduct(String franchiseId, String branchId, String productId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMap(fr -> {
                    var branches = fr.branches().stream().map(b -> {
                        if (!b.id().equals(branchId)) return b;
                        var filtered = b.products().stream()
                                .filter(p -> !p.id().equals(productId))
                                .toList();
                        return new Branch(b.id(), b.name(), filtered);
                    }).toList();
                    return franchiseRepository.save(new Franchise(fr.id(), fr.name(), branches)).then();
                });
    }

    public Mono<Product> updateProductStock(String franchiseId, String branchId,
                                 String productId, int newStock) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMap(fr -> {
                    var branches = fr.branches().stream().map(b -> {
                        if (!b.id().equals(branchId)) return b;
                        var prods = b.products().stream()
                                .map(p -> p.id().equals(productId)
                                        ? new Product(productId, p.name(), newStock)
                                        : p)
                                .toList();
                        return new Branch(b.id(), b.name(), prods);
                    }).toList();
                    var updated = new Franchise(fr.id(), fr.name(), branches);
                    return franchiseRepository.save(updated)
                            .thenReturn(branches.stream()
                                    .filter(b -> b.id().equals(branchId))
                                    .flatMap(b -> b.products().stream())
                                    .filter(p -> p.id().equals(productId))
                                    .findFirst()
                                    .orElseThrow(() -> new NotFoundException("Product not found")));
                });
    }

    public Mono<Product> updateProductName(String franchiseId, String branchId,
                                 String productId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMap(fr -> {
                    var branches = fr.branches().stream().map(b -> {
                        if (!b.id().equals(branchId)) return b;
                        var prods = b.products().stream()
                                .map(p -> p.id().equals(productId)
                                        ? new Product(productId, newName, p.stock())
                                        : p)
                                .toList();
                        return new Branch(b.id(), b.name(), prods);
                    }).toList();
                    var updated = new Franchise(fr.id(), fr.name(), branches);
                    return franchiseRepository.save(updated)
                            .thenReturn(branches.stream()
                                    .filter(b -> b.id().equals(branchId))
                                    .flatMap(b -> b.products().stream())
                                    .filter(p -> p.id().equals(productId))
                                    .findFirst()
                                    .orElseThrow(() -> new NotFoundException("Product not found")));
                });
    }

    public Flux<ProductWithBranch> maxStockByBranch(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMapMany(fr -> Flux.fromIterable(fr.branches()))
                .map(b -> b.products().stream()
                        .max(Comparator.comparingInt(Product::stock))
                        .map(p -> new ProductWithBranch(b.id(), p))
                        .orElseThrow(() -> new NotFoundException("No products in branch")));
    }

    public record ProductWithBranch(String branchId, Product product) {}
}
