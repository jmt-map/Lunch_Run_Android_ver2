package com.example.lunchrun.model;

public class RestaurantPoint {
    private Integer restaurant_id;
    private float lat;
    private float lng;
    private Integer category_id;

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "RestaurantPoint{" +
                "restaurant_id=" + restaurant_id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", category_id=" + category_id +
                '}';
    }
}
