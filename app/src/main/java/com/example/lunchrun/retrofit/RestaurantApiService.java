package com.example.lunchrun.retrofit;

import com.example.lunchrun.model.Restaurant;
import com.example.lunchrun.model.RestaurantCategory;
import com.example.lunchrun.model.RestaurantPoint;
import com.example.lunchrun.model.RestaurantRequestBody;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestaurantApiService {
    @Headers({"Content-Type:application/json"})
    @GET("restaurant")
    Call<List<Restaurant>> getRestaurantList(@Header("x-access-token")String userToken, @Query("category_id")Integer categoryId, @Query("page")Integer page);

    @Headers({"Content-Type:application/json"})
    @HTTP(method = "GET", path = "restaurant", hasBody = true)
    Call<List<Restaurant>> getRestaurants(@Header("x-access-token")String userToken, @Body RestaurantRequestBody body );

    @Headers({"Content-Type:application/json"})
    @GET("restaurant")
    Call<List<RestaurantPoint>> getRestaurantPoints(@Header("x-access-token")String userToken,@Query("category_id")Integer categoryId);


    @Headers({"Content-Type:application/json"})
    @GET("restaurantCategory")
    Call<List<RestaurantCategory>> getRestaurantCategoryList(@Header("x-access-token")String userToken);



}
