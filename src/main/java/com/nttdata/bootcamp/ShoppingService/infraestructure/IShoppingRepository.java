package com.nttdata.bootcamp.ShoppingService.infraestructure;

import com.nttdata.bootcamp.ShoppingService.domain.entity.Shopping;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShoppingRepository extends ReactiveMongoRepository<Shopping, String> {
}
