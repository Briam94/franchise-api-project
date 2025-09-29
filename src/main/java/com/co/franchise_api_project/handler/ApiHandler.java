package com.co.franchise_api_project.handler;

import com.co.franchise_api_project.dto.*;
import com.co.franchise_api_project.usecase.BranchUseCase;
import com.co.franchise_api_project.usecase.FranchiseUseCase;
import com.co.franchise_api_project.usecase.ProductUseCase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ApiHandler {

    @Autowired
    private final FranchiseUseCase franchiseUseCase;
    @Autowired
    private final BranchUseCase branchUseCase;
    @Autowired
    private final ProductUseCase productUseCase;

    public Mono<ServerResponse> createFranchise(ServerRequest req) {
        return req.bodyToMono(FranchiseDto.class)
                .flatMap(dto -> {
                    assert franchiseUseCase != null;
                    return franchiseUseCase.createFranchise(dto.name());
                })
                .flatMap(f -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(f));
    }

    public Mono<ServerResponse> updateFranchiseName(ServerRequest req) {
        var id = req.pathVariable("franchiseId");
        return req.bodyToMono(NameDto.class)
                .flatMap(dto -> {
                    assert franchiseUseCase != null;
                    return franchiseUseCase.updateFranchiseName(id, dto.name());
                })
                .flatMap(f -> ServerResponse.ok().bodyValue(f));
    }

    public Mono<ServerResponse> addBranch(ServerRequest req) {
        var id = req.pathVariable("franchiseId");
        return req.bodyToMono(BranchDto.class)
                .flatMap(dto -> {
                    assert branchUseCase != null;
                    return branchUseCase.addBranch(id, dto.name());
                })
                .flatMap(b -> ServerResponse.ok().bodyValue(b));
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest req) {
        var fid = req.pathVariable("franchiseId");
        var bid = req.pathVariable("branchId");
        return req.bodyToMono(NameDto.class)
                .flatMap(dto -> {
                    assert branchUseCase != null;
                    return branchUseCase.updateBranchName(fid, bid, dto.name());
                })
                .flatMap(b -> ServerResponse.ok().bodyValue(b));
    }

    public Mono<ServerResponse> addProduct(ServerRequest req) {
        var fid = req.pathVariable("franchiseId");
        var bid = req.pathVariable("branchId");
        return req.bodyToMono(ProductDto.class)
                .flatMap(dto -> {
                    assert productUseCase != null;
                    return productUseCase.addProduct(fid, bid, dto.name(), dto.stock());
                })
                .flatMap(p -> ServerResponse.ok().bodyValue(p));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest req) {
        var fid = req.pathVariable("franchiseId");
        var bid = req.pathVariable("branchId");
        var pid = req.pathVariable("productId");
        assert productUseCase != null;
        return productUseCase.deleteProduct(fid, bid, pid)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateProductStock(ServerRequest req) {
        var fid = req.pathVariable("franchiseId");
        var bid = req.pathVariable("branchId");
        var pid = req.pathVariable("productId");
        return req.bodyToMono(StockDto.class)
                .flatMap(dto -> {
                    assert productUseCase != null;
                    return productUseCase.updateProductStock(fid, bid, pid, dto.stock());
                })
                .flatMap(p -> ServerResponse.ok().bodyValue(p));
    }

    public Mono<ServerResponse> updateProductName(ServerRequest req) {
        var fid = req.pathVariable("franchiseId");
        var bid = req.pathVariable("branchId");
        var pid = req.pathVariable("productId");
        return req.bodyToMono(NameDto.class)
                .flatMap(dto -> {
                    assert productUseCase != null;
                    return productUseCase.updateProductName(fid, bid, pid, dto.name());
                })
                .flatMap(p -> ServerResponse.ok().bodyValue(p));
    }

    public Mono<ServerResponse> getMaxStock(ServerRequest req) {
        var fid = req.pathVariable("franchiseId");
        assert productUseCase != null;
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productUseCase.maxStockByBranch(fid)
                                .map(x -> new ProductWithBranchDto(
                                        x.branchId(), x.product().id(),
                                        x.product().name(), x.product().stock())),
                        ProductWithBranchDto.class);
    }

}
