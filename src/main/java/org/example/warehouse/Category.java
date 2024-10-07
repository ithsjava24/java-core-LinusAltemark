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
            throw new IllegalArgumentException("Category name can't be null");
        }

        // Ser till att första bokstaven i namnet är Stor
        String formattedName = capitalizeFirstLetter(name);

        // Kollar ifall kateogrin redan existerar i cachen.
        // Om inte så skapas det en ny kategori som läggs till i cachen
        return cache.computeIfAbsent(formattedName, Category::new);
    }

    // En Helper-metod som används för att göra första bokstaven stor
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
