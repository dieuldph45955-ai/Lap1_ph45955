package com.example.myapplication.Model;

public class CatDTO {
    private int id;
    private String name;

    public CatDTO() {}

    public CatDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CatDTO(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}