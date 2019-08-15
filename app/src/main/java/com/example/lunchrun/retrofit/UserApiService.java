package com.example.lunchrun.retrofit;

import com.example.lunchrun.model.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApiService {

    @FormUrlEncoded
    @POST("auth/signup")
    Call<Object> createUser(@FieldMap HashMap<String, Object> param);

    @Headers({"Content-Type:application/json"})
    @GET("user")
    Call<User> getUser(
            @Query("email") String email,
            @Query("password") String password
    );

    @FormUrlEncoded
    @POST("auth/login")
    Call<Object> getLoginToken(@FieldMap HashMap<String, Object> param);

    @Headers({"Content-Type:application/json"})
    @GET("user")
    Call<List<User>> getAllUsers();


}