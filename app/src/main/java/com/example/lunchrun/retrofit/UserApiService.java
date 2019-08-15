package com.example.lunchrun.retrofit;

import com.example.lunchrun.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApiService {

    @FormUrlEncoded
    @POST("auth/signup")
    Call<String> createUser(
            @Field("alias") String alias,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone
    );

    @GET("user")
    Call<User> getUser(
            @Query("email") String email,
            @Query("password") String password
    );
}