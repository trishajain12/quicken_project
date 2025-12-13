package com.example.demo.model;

public class Account {
    private long id;
    private String name;
    private String description;

    public Account(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
