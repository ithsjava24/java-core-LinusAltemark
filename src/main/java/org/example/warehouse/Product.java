package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID uuid, String name, Category category, BigDecimal price) {
}
