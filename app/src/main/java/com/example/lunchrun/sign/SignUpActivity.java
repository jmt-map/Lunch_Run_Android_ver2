package com.example.lunchrun.sign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lunchrun.MainActivity;
import com.example.lunchrun.R;
import com.example.lunchrun.base.UserInfo;
import com.example.lunchrun.model.User;
import com.example.lunchrun.retrofit.ApiClient;
import com.example.lunchrun.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends Activity {
    private EditText etName;
    private EditText etEmail;
    private EditText etPwd;
    private EditText etPhone;
    private Button btnSignup;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        etPhone = findViewById(R.id.et_phone);
        btnSignup = findViewById(R.id.btn_signup);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();

                String name = etName.getText().toString();
                if(name != null && name.length()>0)
                    user.setAlias(name);

                else{
                    // 이름 입력
                }

                String email = etEmail.getText().toString();
                if(email!=null && email.length()>0){
                    user.setEmail(email);
                }
                else{

                }

                String pwd = etPwd.getText().toString();
                if(pwd!=null && pwd.length()>=4){
                    user.setPassword(pwd);
                }
                else{

                }

                String phone = etPhone.getText().toString();
                if(phone!=null && phone.length()>0){
                    user.setPhone(phone);
                }
                else{

                }

                // Create User Request
                Call<String> call = apiService.createUser(user.getAlias(), user.getEmail(),user.getPassword(), user.getPhone());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("SIGN UP", "CODE="+String.valueOf(response.code()));
                        Log.d("SIGN UP", response.body().toString());

                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("SIGN UP", "ERRORS!");
                        Log.e("SIGN UP", t.toString());
                    }
                });
            }
        });

    }

}
