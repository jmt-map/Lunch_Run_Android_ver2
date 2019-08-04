package com.example.lunchrun.model;

import java.sql.Timestamp;

public class ReviewDetail {
    private Integer id;
    private Timestamp create_datetime;
    private Timestamp dalete_datetime;
    private Float rating;
    private String comment;
    private Integer user_id;
    private Integer restaurant_id;

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", create_datetime=" + create_datetime +
                ", dalete_datetime=" + dalete_datetime +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", user_id=" + user_id +
                ", restaurant_id=" + restaurant_id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(Timestamp create_datetime) {
        this.create_datetime = create_datetime;
    }

    public Timestamp getDalete_datetime() {
        return dalete_datetime;
    }

    public void setDalete_datetime(Timestamp dalete_datetime) {
        this.dalete_datetime = dalete_datetime;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
