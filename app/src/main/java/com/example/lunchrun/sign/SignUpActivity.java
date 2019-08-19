package com.example.lunchrun.sign;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lunchrun.MainActivity;
import com.example.lunchrun.R;
import com.example.lunchrun.model.User;
import com.example.lunchrun.retrofit.ApiClient;
import com.example.lunchrun.retrofit.UserApiService;
import com.example.lunchrun.utils.AsteriskPasswordTransformationMethod;

import org.json.JSONArray;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends Activity {
    private EditText etName;
    private EditText etEmail;
    private EditText etPwd;
    private EditText etPhone;
    private Button btnSignup;
    private UserApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(SignUpActivity.this, R.color.black));

        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);

        etPwd = findViewById(R.id.et_pwd);
        etPwd.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        etPhone = findViewById(R.id.et_phone);
        btnSignup = findViewById(R.id.btn_signup);

        apiService = ApiClient.getClient().create(UserApiService.class);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();

                String name = etName.getText().toString();
                if(name != null && name.length()>0)
                    user.setAlias(name);

                else{
                    // 이름 입력
                    Toast.makeText(getApplicationContext(),"이름을 입력하세요",Toast.LENGTH_SHORT);
                }

                String email = etEmail.getText().toString();
                if(email!=null && email.length()>0){
                    user.setEmail(email);
                }
                else{
                    // 이메일 입력
                    Toast.makeText(getApplicationContext(),"이메일을 입력하세요",Toast.LENGTH_SHORT);
                }

                String pwd = etPwd.getText().toString();
                if(pwd!=null && pwd.length()>=4){
                    user.setPassword(pwd);
                }
                else{
                    // 비밀번호 입력
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력하세요",Toast.LENGTH_SHORT);
                }

                String phone = etPhone.getText().toString();
                if(phone!=null && phone.length()>0){
                    phone = phone.substring(0,3) + "-" + phone.substring(3,7) + "-" + phone.substring(7);
                    user.setPhone(phone);
                }
                else{
                    // 전화번호 입력
                    Toast.makeText(getApplicationContext(),"전화번호를 입력하세요",Toast.LENGTH_SHORT);
                }

                // Create User Request
                HashMap<String, Object> param = new HashMap<>();
                param.put("email", user.getEmail());
                param.put("password", user.getPassword());
                param.put("alias", user.getAlias());
                param.put("phone", user.getPhone());
                Log.e("SIGN UP", param.toString());

                Call<Object> call = apiService.createUser(param);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.d("SIGN UP", "CODE="+String.valueOf(response.code()));
                        if(response.message()!=null)
                            Log.d("SIGN UP", "RESPONSE " + response.message());
                        if(response.errorBody()!=null) {
                            try {
                                Log.d("SIGN UP", "RESPONSE " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if( response.body() !=null){
                            if(response.code()==200)
                                Log.d("SIGN UP", String.valueOf((Long)response.body()));

                            else
                                Log.d("SIGN UP", (String)response.body());
                        }
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                        Log.e("SIGN UP", "ERRORS!");
                        Log.e("SIGN UP", t.toString());
                    }
                });
            }
        });
    }

}
