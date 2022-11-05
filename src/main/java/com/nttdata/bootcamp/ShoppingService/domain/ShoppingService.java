package com.nttdata.bootcamp.ShoppingService.domain;

import com.nttdata.bootcamp.ShoppingService.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingRequest;
import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingResponse;
import com.nttdata.bootcamp.ShoppingService.infraestructure.IShoppingMapper;
import com.nttdata.bootcamp.ShoppingService.infraestructure.IShoppingRepository;
import com.nttdata.bootcamp.ShoppingService.infraestructure.IShoppingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingService implements IShoppingService {
    @Autowired
    private final IShoppingRepository repository;
    @Autowired
    private final IShoppingMapper mapper;
    @Autowired
    private final CustomerActiveProductRepository customerProductRepository;

    @Override
    public Flux<ShoppingResponse> getAll() {
        log.info("====> ShoppingService: GetAll");
        return repository.findAll()
                .map(mapper::toResponse);
    }

    @Override
    public Mono<ShoppingResponse> getById(String id) {
        log.info("====> ShoppingService: GetById");
        return repository.findById(id)
                .map(mapper::toResponse)
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<ShoppingResponse> save(Mono<ShoppingRequest> request) {
        log.info("====> ShoppingService: Save");
        // Existe la cuenta?
        return request.map(this::printDebug)
                .flatMap(e ->
                        customerProductRepository.getById(e.getCustomerActiveProductId())
                                .flatMap(customerActiveProductResponse -> {
                                    // Coloca la fecha
                                    e.setDate(getDate());
                                    // Tiene saldo?
                                    if ((customerActiveProductResponse.getLineOfCredit() - customerActiveProductResponse.getDebt()) < e.getAmount()) {
                                        // Retorna error
                                        return Mono.error(RuntimeException::new);
                                    }
                                    CustomerActiveProductRequest update = new CustomerActiveProductRequest();
                                    BeanUtils.copyProperties(customerActiveProductResponse, update);
                                    update.setDebt(customerActiveProductResponse.getDebt() + e.getAmount());
                                    // Actualizar saldo
                                    return customerProductRepository.update(update, e.getCustomerActiveProductId())
                                            // Guardar operacion
                                            .flatMap(p -> Mono.just(e))
                                            .map(mapper::toEntity)
                                            .flatMap(repository::save)
                                            .map(mapper::toResponse)
                                            .switchIfEmpty(Mono.error(RuntimeException::new));
                                })
                                // No Existe
                                // Mandar error
                                .switchIfEmpty(Mono.error(RuntimeException::new)));
    }

    @Override
    public Mono<ShoppingResponse> update(Mono<ShoppingRequest> request, String id) {
        log.info("====> ShoppingService: Update");
        return Mono.just(new ShoppingResponse());
    }

    @Override
    public Mono<ShoppingResponse> delete(String id) {
        log.info("====> ShoppingService: Delete");
        return repository.findById(id)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(deleteCustomer -> repository.delete(deleteCustomer)
                        .then(Mono.just(mapper.toResponse(deleteCustomer))));
    }

    public String getDate() {
        log.info("====> ShoppingService: GetDate");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public ShoppingRequest printDebug(ShoppingRequest request) {
        log.info("====> ShoppingService: printDebug");
        log.info("====> ShoppingService: Request ==> " + request.toString());
        return request;
    }
}
