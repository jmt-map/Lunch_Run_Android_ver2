package com.example.lunchrun.retrofit;

import com.example.lunchrun.model.TestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface TestApiService {
    @Headers({"Content-Type:application/json"})
    @GET("test")
    Call<TestModel> getTestModel();
}
