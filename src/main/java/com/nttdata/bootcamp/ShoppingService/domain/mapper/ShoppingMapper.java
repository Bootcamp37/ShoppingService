package com.nttdata.bootcamp.ShoppingService.domain.mapper;

import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingRequest;
import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingResponse;
import com.nttdata.bootcamp.ShoppingService.domain.entity.Shopping;
import org.springframework.stereotype.Component;

@Component
public class ShoppingMapper implements IShoppingMapper {
    @Override
    public Shopping toEntity(ShoppingRequest request) {
        Shopping shopping = new Shopping();
        shopping.setCustomerActiveProductId(request.getCustomerActiveProductId());
        shopping.setAmount(request.getAmount());
        shopping.setShoppingDate(request.getShoppingDate());
        return shopping;
    }

    @Override
    public ShoppingResponse toResponse(Shopping shopping) {
        ShoppingResponse shoppingResponse = new ShoppingResponse();
        shoppingResponse.setId(shopping.getId());
        shoppingResponse.setCustomerActiveProductId(shopping.getCustomerActiveProductId());
        shoppingResponse.setAmount(shopping.getAmount());
        shoppingResponse.setShoppingDate(shopping.getShoppingDate());
        return shoppingResponse;
    }
}
