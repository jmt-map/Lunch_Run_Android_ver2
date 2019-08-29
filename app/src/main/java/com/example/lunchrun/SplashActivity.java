package com.example.lunchrun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.lunchrun.base.CategoryTagSharedPreference;
import com.example.lunchrun.base.SavedSharedPreference;
import com.example.lunchrun.base.UserInfo;
import com.example.lunchrun.model.RestaurantCategory;
import com.example.lunchrun.model.Tag;
import com.example.lunchrun.model.User;
import com.example.lunchrun.retrofit.ApiClient;
import com.example.lunchrun.retrofit.RestaurantApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity  extends Activity {
    private RestaurantApiService apiService;
    private Handler hd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        apiService = ApiClient.getClient().create(RestaurantApiService.class);
        CategoryTagSharedPreference.setContext(SplashActivity.this);
        UserInfo.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OCwiYWxpYXMiOiJpbmFpbmEiLCJpYXQiOjE1NjcwNTE0NjgsImV4cCI6MTU2ODI2MTA2OCwiaXNzIjoiaW5hQHRlc3QuY29tIiwic3ViIjoidXNlckluZm8ifQ.Mq3tICdwtuARNhnlKfkt7wIujE2M7_z_cDsTRWmFGj4");

        // 카테고리
        Call<List<RestaurantCategory>> categoryCall = apiService.getRestaurantCategoryList(UserInfo.getToken());
        categoryCall.enqueue(new Callback<List<RestaurantCategory>>() {
            @Override
            public void onResponse(Call<List<RestaurantCategory>> call, Response<List<RestaurantCategory>> response) {
                Log.d("REST CATEGORY", "CODE"+response.code());

                if(response.code()==200){
                    ArrayList<String> categoriesStr = new ArrayList<>();
                    for(RestaurantCategory category : response.body()){
                        categoriesStr.add(category.getName());
                    }
                    CategoryTagSharedPreference.setCategoryList(categoriesStr);
                }
            }
            @Override
            public void onFailure(Call<List<RestaurantCategory>> call, Throwable t) {

            }
        });

        // 태그
        Call<List<Tag>> tagCall = apiService.getTagList(UserInfo.getToken());
        tagCall.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                Log.d("REST TAG", "CODE "+response.code());
                if( response.code()==200){
                    ArrayList<String> tags = new ArrayList<>();
                    for(Tag tag : response.body()){
                        tags.add(tag.getName());
                    }
                    CategoryTagSharedPreference.setTagList(tags);
                    hd = new Handler();
                    hd.postDelayed(new splashhandler(), 2000); // 1초 후에 hd handler 실행  3000ms = 3초
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {

            }
        });

        if( hd==null){
            hd = new Handler();
            hd.postDelayed(new splashhandler(), 2000); // 1초 후에 hd handler 실행  3000ms = 3초
        }

    }

    private class splashhandler implements Runnable{
        public void run(){
            startActivity(new Intent(SplashActivity.this, MainActivity.class)); //로딩이 끝난 후, ChoiceFunction 이동
            SplashActivity.this.finish(); // 로딩페이지 Activity stack에서 제거
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

}
