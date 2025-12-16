package com.example.demo.model;

public record Account(long id, String name, String description) {
    public Account(long id, String name, String description) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        } else this.id = id;

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be blank");
        } else this.name = name;

        this.description = description;
    }
}
