package com.example.lunchrun.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("post")
    Call<String> createUser(
            @Field("alias") String alias,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone
    );
}