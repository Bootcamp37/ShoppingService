package com.nttdata.bootcamp.ShoppingService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingResponse {
    private String id;
    private String customerActiveProductId;
    private Double amount;
    private String shoppingDate;
}
