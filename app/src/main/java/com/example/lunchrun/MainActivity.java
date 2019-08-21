package com.example.lunchrun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lunchrun.base.UserInfo;
import com.example.lunchrun.model.TestModel;
import com.example.lunchrun.model.User;
import com.example.lunchrun.retrofit.ApiClient;
import com.example.lunchrun.retrofit.TestApiService;
import com.example.lunchrun.retrofit.UserApiService;
import com.example.lunchrun.sign.SignInActivity;
import com.example.lunchrun.sign.SignUpActivity;
import com.example.lunchrun.utils.AsteriskPasswordTransformationMethod;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnSignin;
    private TextView btnSignup;
    private String appKey;
    private EditText etEmail, etPwd;
    private boolean loginFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.black));

        setContentView(R.layout.activity_new_main);
        getAllUsers();

        btnSignin = findViewById(R.id.btn_signin);
        btnSignup = findViewById(R.id.btn_signup);
        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);

        etPwd.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String pwd = etPwd.getText().toString();
                if(email == null || pwd ==null){
                    Toast.makeText(getApplicationContext(),"모든 항목을 입력해주세요",Toast.LENGTH_SHORT);
                }
                else{
                    login(email, pwd);
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(final String email, final String pwd){
        loginFlag = false;
        HashMap<String, Object> param = new HashMap<>();
        param.put("email", email);
        param.put("password", pwd);

        UserApiService apiService = ApiClient.getClient().create(UserApiService.class);
        Call<Object> call = apiService.getLoginToken(param);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("SIGN IN", "CODE " + String.valueOf(response.code()));
                if(response.code()==200) {
                    loginFlag = true;
                    if (response.body() != null) {
                        Log.d("SIGN IN token", response.body().toString());
                        User user = new User();
                        user.setEmail(email);
                        user.setPassword(pwd);
                        UserInfo.setUser(user);
                        String token = response.body().toString().substring(7);
                        token = token.substring(0,token.length()-1);
                        Log.d("SIGN IN token",token);
                        UserInfo.setToken(token);
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("SIGN IN", "ERRORS!");
                Log.e("SIGN IN", t.toString());
                Toast.makeText(getApplicationContext(),"잘못된 이메일 혹은 비밀번호 입니다.", Toast.LENGTH_SHORT);
            }
        });
    }


    public void getAllUsers(){
        UserApiService apiService = ApiClient.getClient().create(UserApiService.class);
        Call<List<User>> call = apiService.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("SIGN IN", "CODE " + String.valueOf(response.code()));
                Log.d("SIGN IN", response.body().toString());

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("SIGN IN", "ERRORS!");
                Log.e("SIGN IN", t.toString());
            }
        });
    }

}
