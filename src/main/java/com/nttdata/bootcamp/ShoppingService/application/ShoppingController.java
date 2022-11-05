package com.nttdata.bootcamp.ShoppingService.application;

import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingRequest;
import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingResponse;
import com.nttdata.bootcamp.ShoppingService.infraestructure.IShoppingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("${message.path-shopping}")
@RefreshScope
@Slf4j
public class ShoppingController {
    @Autowired
    private final IShoppingService service;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Flux<ShoppingResponse> getAll() {
        log.info("====> ShoppingController: GetAll");
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Mono<ShoppingResponse>> getById(@PathVariable String id) {
        log.info("====> ShoppingController: GetById");
        Mono<ShoppingResponse> shoppingResponseMono = service.getById(id);
        return new ResponseEntity<>(shoppingResponseMono, shoppingResponseMono != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ShoppingResponse> save(@RequestBody ShoppingRequest request) {
        log.info("====> ShoppingController: Save");
        return service.save(Mono.just(request));
    }

    @PutMapping("/update/{id}")
    public Mono<ShoppingResponse> update(@RequestBody ShoppingRequest request, @PathVariable String id) {
        log.info("====> ShoppingController: Update");
        return service.update(Mono.just(request), id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        log.info("====> ShoppingController: Delete");
        return service.delete(id)
                .map(r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
