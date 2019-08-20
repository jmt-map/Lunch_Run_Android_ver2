package com.example.lunchrun.model;

public class RestaurantCategory {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "RestaurantCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
