package com.nttdata.bootcamp.ShoppingService.infraestructure;

import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingRequest;
import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IShoppingService {
    Flux<ShoppingResponse> getAll();

    Mono<ShoppingResponse> getById(String id);

    Mono<ShoppingResponse> save(Mono<ShoppingRequest> request);

    Mono<ShoppingResponse> update(Mono<ShoppingRequest> request, String id);

    Mono<ShoppingResponse> delete(String id);
}
