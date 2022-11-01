package com.nttdata.bootcamp.ShoppingService.domain;

import com.nttdata.bootcamp.ShoppingService.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingRequest;
import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingResponse;
import com.nttdata.bootcamp.ShoppingService.infraestructure.IShoppingMapper;
import com.nttdata.bootcamp.ShoppingService.infraestructure.IShoppingRepository;
import com.nttdata.bootcamp.ShoppingService.infraestructure.IShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ShoppingService implements IShoppingService {
    @Autowired
    private final IShoppingRepository repository;
    @Autowired
    private final IShoppingMapper mapper;
    @Autowired
    private final CustomerActiveProductRepository customerProductRepository;

    @Override
    public Flux<ShoppingResponse> getAll() {
        return repository.findAll()
                .map(mapper::toResponse);
    }

    @Override
    public Mono<ShoppingResponse> getById(String id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<ShoppingResponse> save(ShoppingRequest request) {
        // Existe la cuenta?
        return customerProductRepository.getById(request.getCustomerActiveProductId())
                .flatMap(customerActiveProductResponse -> {
                    // Coloca la fecha
                    request.setShoppingDate(getDate());
                    // Tiene saldo?
                    if ((customerActiveProductResponse.getLineOfCredit() - customerActiveProductResponse.getDebt()) < request.getAmount()) {
                        // Retorna error
                        return Mono.error(RuntimeException::new);
                    }

                    CustomerActiveProductRequest update = new CustomerActiveProductRequest();
                    BeanUtils.copyProperties(customerActiveProductResponse, update);
                    update.setDebt(customerActiveProductResponse.getDebt() + request.getAmount());
                    // Actualizar saldo
                    return customerProductRepository.update(update, request.getCustomerActiveProductId())
                            // Guardar operacion
                            .flatMap(p -> Mono.just(request))
                            .map(mapper::toEntity)
                            .flatMap(repository::save)
                            .map(mapper::toResponse)
                            .switchIfEmpty(Mono.error(RuntimeException::new));
                })
                // No Existe
                // Mandar error
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<ShoppingResponse> update(ShoppingRequest request, String id) {
        return Mono.just(new ShoppingResponse());
    }

    @Override
    public Mono<ShoppingResponse> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(deleteCustomer -> repository.delete(deleteCustomer)
                        .then(Mono.just(mapper.toResponse(deleteCustomer))));
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
