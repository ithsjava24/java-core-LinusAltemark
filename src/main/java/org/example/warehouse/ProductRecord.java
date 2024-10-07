package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRecord(UUID uuid, String name, Category category, BigDecimal price){

    public ProductRecord(UUID uuid, String name, Category category, BigDecimal price) {
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        this.uuid = uuid != null ? uuid : UUID.randomUUID();
        this.name = name;
        this.category = category;
        this.price = price != null ? price : BigDecimal.ZERO;
    }

}
