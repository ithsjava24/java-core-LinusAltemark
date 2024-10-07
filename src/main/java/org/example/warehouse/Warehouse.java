package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Warehouse {

    private String name;
    private Map<UUID, ProductRecord> products = new ConcurrentHashMap<>();
    private Set<ProductRecord> changedProducts = new HashSet<>();

    private static final Map<String, Warehouse> instances = new HashMap<>();

    private Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance() {
        return getInstance("Warehouse");
    }

    public static Warehouse getInstance(String name) {
        return instances.computeIfAbsent(name, Warehouse::new);
    }

    // Kontrollerar ifall lagret är tomt
    public boolean isEmpty(){
        return products.isEmpty();
    }

    // Hämtar alla produkter
    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }
}
