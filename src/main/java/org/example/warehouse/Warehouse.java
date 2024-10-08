package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;

public class Warehouse {
    private static final Map<String, Warehouse> instances = new HashMap<>();
    private final String name;
    private final List<Product> products = new ArrayList<>();
    private final Set<Product> changedProducts = new HashSet<>();

    private Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance() {
        return new Warehouse("");
    }

    public static Warehouse getInstance(String name) {
        return instances.computeIfAbsent(name, Warehouse::new);
    }

    public Product addProduct(UUID uuid, String productName, Category category, BigDecimal price) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (price == null) {
            price = BigDecimal.ZERO;
        }

        for (Product product : products) {
            if (product.uuid().equals(uuid)) {
                throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
            }
        }

        Product newProduct = new Product(uuid, productName, category, price);
        products.add(newProduct);
        return newProduct;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public Optional<Product> getProductById(UUID id) {
        return products.stream()
                .filter(product -> product.uuid().equals(id))
                .findFirst();
    }

    public void updateProductPrice(UUID id, BigDecimal newPrice) {
        var product = getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with that id doesn't exist."));
        changedProducts.add(product);
        products.remove(product);
        products.add(new Product(product.uuid(), product.name(), product.category(), newPrice));
    }

    public Set<Product> getChangedProducts() {
        return changedProducts;
    }

    public List<Product> getProductsBy(Category category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .toList();
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        Map<Category, List<Product>> groupedProducts = new HashMap<>();
        for (Product product : products) {
            groupedProducts.computeIfAbsent(product.category(), k -> new ArrayList<>()).add(product);
        }
        return groupedProducts;
    }
}
