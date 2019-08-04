package com.example.lunchrun.sign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lunchrun.MainActivity;
import com.example.lunchrun.R;
import com.example.lunchrun.base.UserInfo;
import com.example.lunchrun.model.User;


public class SignUpActivity extends Activity {
    private EditText etName;
    private EditText etEmail;
    private EditText etPwd;
    private EditText etPhone;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        etPhone = findViewById(R.id.et_phone);
        btnSignup = findViewById(R.id.btn_signup);

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

                UserInfo.setUser(user);

                // user 등록 후 메인 페이지로 이동
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
