package org.example.warehouse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Category {

    private String name;

    private static final Map<String, Category> cache = new ConcurrentHashMap<>();

    private Category(String name) {
        this.name = name;
    }

    public static Category of (String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name cannot be null");
        }

        String formattedName = capitalizeFirstLetter(name);

        return cache.computeIfAbsent(name, Category::new);
    }

    private static String capitalizeFirstLetter(String name) {
        if (name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getName() {
        return name;
    }
}
