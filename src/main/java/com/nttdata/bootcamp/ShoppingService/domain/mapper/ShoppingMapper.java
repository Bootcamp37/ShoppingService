package com.nttdata.bootcamp.ShoppingService.domain.mapper;

import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingRequest;
import com.nttdata.bootcamp.ShoppingService.domain.dto.ShoppingResponse;
import com.nttdata.bootcamp.ShoppingService.domain.entity.Shopping;
import com.nttdata.bootcamp.ShoppingService.infraestructure.IShoppingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShoppingMapper implements IShoppingMapper {
    @Override
    public Shopping toEntity(ShoppingRequest request) {
        log.info("====> ShoppingMapper: ToEntity");
        Shopping shopping = new Shopping();
        BeanUtils.copyProperties(request, shopping);
        return shopping;
    }

    @Override
    public ShoppingResponse toResponse(Shopping shopping) {
        log.info("====> ShoppingMapper: ToResponse");
        ShoppingResponse shoppingResponse = new ShoppingResponse();
        BeanUtils.copyProperties(shopping, shoppingResponse);
        return shoppingResponse;
    }
}
