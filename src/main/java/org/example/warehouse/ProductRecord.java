package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRecord(UUID uuid, String name, Category category, BigDecimal price){

    public ProductRecord(UUID uuid, String name, Category category, BigDecimal price) {
        this.uuid = uuid;
        this.name = name;
        this.category = category;
        this.price = price != null ? price : BigDecimal.ZERO;

        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }

        if(category == null){
            throw new IllegalArgumentException("Category can't be null.");
        }
    }
}
