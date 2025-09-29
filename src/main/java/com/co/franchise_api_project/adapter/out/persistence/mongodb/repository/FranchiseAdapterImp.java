package com.co.franchise_api_project.adapter.out.persistence.mongodb.repository;

import com.co.franchise_api_project.adapter.out.persistence.mongodb.entity.BranchEntity;
import com.co.franchise_api_project.adapter.out.persistence.mongodb.entity.FranchiseEntity;
import com.co.franchise_api_project.adapter.out.persistence.mongodb.entity.ProductEntity;
import com.co.franchise_api_project.domain.model.Branch;
import com.co.franchise_api_project.domain.model.Franchise;
import com.co.franchise_api_project.domain.model.Product;
import com.co.franchise_api_project.domain.port.FranchiseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class FranchiseAdapterImp implements FranchiseRepository {

    private final FranchiseMongoRepository franchiseMongoRepository;

    @Override
    public Mono<Franchise> save(Franchise f) {
        return franchiseMongoRepository.save(toEntity(f))
                .map(this::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return franchiseMongoRepository.findById(id)
                .map(this::toDomain);
    }

    private FranchiseEntity toEntity(Franchise f) {
        var branches = f.branches().stream()
                .map(b -> new BranchEntity(
                        b.id(), b.name(),
                        b.products().stream()
                                .map(p -> new ProductEntity(p.id(), p.name(), p.stock()))
                                .toList()))
                .toList();
        return new FranchiseEntity(f.id(), f.name(), branches);
    }

    private Franchise toDomain(FranchiseEntity e) {
        var branches = e.getBranches().stream()
                .map(b -> new Branch(
                        b.getId(), b.getName(),
                        b.getProducts().stream()
                                .map(p -> new Product(p.getId(), p.getName(), p.getStock()))
                                .toList()))
                .toList();
        return new Franchise(e.getId(), e.getName(), branches);
    }
}
