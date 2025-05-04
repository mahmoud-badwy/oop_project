package com.example.oop_java_ui;

import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private List<Category> categories;
    private int nextId;

    public CategoryManager() {
        this.categories = new ArrayList<>();
        this.nextId = 1;
    }

    // Create a new category
    public Category createCategory(String name, String description) {
        Category category = new Category(nextId++, name, description);
        categories.add(category);
        return category;
    }

    // Read all categories
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    // Read a specific category by ID
    public Category getCategoryById(int id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    // Update a category
    public boolean updateCategory(int id, String name, String description) {
        Category category = getCategoryById(id);
        if (category != null) {
            category.setName(name);
            category.setDescription(description);
            return true;
        }
        return false;
    }

    // Delete a category
    public boolean deleteCategory(int id) {
        Category category = getCategoryById(id);
        if (category != null) {
            categories.remove(category);
            return true;
        }
        return false;
    }

    // Search categories by name
    public List<Category> searchCategoriesByName(String name) {
        List<Category> results = new ArrayList<>();
        for (Category category : categories) {
            if (category.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(category);
            }
        }
        return results;
    }
} 