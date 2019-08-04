package com.example.lunchrun.model;

import java.sql.Timestamp;
import java.util.List;

public class Review {

    List<ReviewDetail> details;

    @Override
    public String toString() {
        return "Review{" +
                "details=" + details +
                '}';
    }

    public List<ReviewDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ReviewDetail> details) {
        this.details = details;
    }
}