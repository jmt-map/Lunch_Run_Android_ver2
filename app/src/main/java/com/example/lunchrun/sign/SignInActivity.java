package com.example.lunchrun.sign;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lunchrun.HomeActivity;
import com.example.lunchrun.MainActivity;
import com.example.lunchrun.R;
import com.example.lunchrun.base.UserInfo;
import com.example.lunchrun.model.User;
import com.example.lunchrun.retrofit.ApiClient;
import com.example.lunchrun.retrofit.UserApiService;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends Activity {
    private EditText etEmail;
    private EditText etPwd;
    private Button btnSignin;
    private UserApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(SignInActivity.this, R.color.black));

        setContentView(R.layout.activity_signin);

        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        btnSignin = findViewById(R.id.btn_signin);

        apiService = ApiClient.getClient().create(UserApiService.class);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();
                if(email==null || email.length()==0){
                    //alert
                }

                String pwd = etPwd.getText().toString();
                if(pwd==null || pwd.length()<4){
                    //alert
                }

                Call<User> call = apiService.getUser(email, pwd);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("SIGN IN", "CODE " + String.valueOf(response.code()));
                        User user = response.body();

                        // 등록된 user인 경우 로그인 진행
                        if(user != null){
                            Log.d("SIGN IN", "User : " + user.getAlias());
                            UserInfo.setUser(user);

                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        else{
                            //alert
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("SIGN IN", "ERRORS!");
                        Log.e("SIGN IN", t.toString());
                    }
                });
            }
        });
    }



}
