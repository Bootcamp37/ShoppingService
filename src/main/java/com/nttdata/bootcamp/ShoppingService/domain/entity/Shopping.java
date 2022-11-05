package com.nttdata.bootcamp.ShoppingService.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shoppings")
public class Shopping {
    @Id
    private String id;
    private String customerActiveProductId;
    private Double amount;
    private String date;
}
