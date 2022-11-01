package com.nttdata.bootcamp.ShoppingService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerActiveProductRequest {
    private String customerId;
    private String productId;
    private Double lineOfCredit;
    private Double debt;
}
