package com.nttdata.bootcamp.ShoppingService.application;

import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingRequest;
import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingResponse;
import com.nttdata.bootcamp.ShoppingService.infraestructure.IShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("${message.path-shopping}")
@RefreshScope
public class ShoppingController {
    @Autowired
    private final IShoppingService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ShoppingResponse> getAll() {
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<ShoppingResponse> getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public Mono<ShoppingResponse> save(@RequestBody ShoppingRequest request) {
        return service.save(request);
    }

    @PutMapping("/update/{id}")
    public Mono<ShoppingResponse> update(@RequestBody ShoppingRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ShoppingResponse> delete(@PathVariable String id) {
        return service.delete(id);
    }
}
