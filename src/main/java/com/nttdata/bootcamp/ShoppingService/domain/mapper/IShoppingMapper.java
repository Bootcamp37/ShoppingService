package com.nttdata.bootcamp.ShoppingService.domain.mapper;

import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingRequest;
import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingResponse;
import com.nttdata.bootcamp.ShoppingService.domain.entity.Shopping;

public interface IShoppingMapper {
    Shopping toEntity(ShoppingRequest request);

    ShoppingResponse toResponse(Shopping shopping);
}
