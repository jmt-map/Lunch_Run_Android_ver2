package com.example.lunchrun.model;

public class RestaurantRequestBody {
    private Integer category_id;
    private Integer page;

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "RestaurantRequestBody{" +
                "category_id=" + category_id +
                ", page=" + page +
                '}';
    }
}
