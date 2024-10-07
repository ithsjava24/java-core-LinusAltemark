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

    // Lägg till en produkt
    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price){
        if (uuid != null && products.containsKey(uuid)){
            throw new IllegalArgumentException("Product with that ID already exists");
        }

        ProductRecord product = new ProductRecord(uuid, name, category, price);
        products.put(product.uuid(), product);
        return product;
    }

    // Hämta produkt med ID
    public Optional<ProductRecord> getProductById(UUID uuid){
        return Optional.ofNullable(products.get(uuid));
    }

    // Uppdaterar priset för produkt
    public void updateProductPrice(UUID uuid, BigDecimal newPrice){
        ProductRecord product = products.get(uuid);

        if (product == null){
            throw new IllegalArgumentException("Product with that ID does not exist");
        }

        ProductRecord updatedProduct = new ProductRecord(product.uuid(), product.name(), product.category(), newPrice);
        products.put(uuid, updatedProduct);
        changedProducts.add(updatedProduct);
    }

    // Hämta ändrade produkter
    public Set<ProductRecord> getChangedProducts() {
        return Collections.unmodifiableSet(changedProducts);
    }

    // Grupperar produkterna efter kategorier
    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.values().stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }

    // Hämta produkt från specifik kategori
    public List<ProductRecord> getProductsBy(Category category) {
        return products.values().stream()
                .filter(product -> product.category().equals(category))
                .collect(Collectors.toList());
    }
}
