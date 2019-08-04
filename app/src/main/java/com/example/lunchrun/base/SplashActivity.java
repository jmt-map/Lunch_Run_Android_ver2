package com.example.lunchrun.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.lunchrun.R;
import com.example.lunchrun.sign.SignUpActivity;

public class SplashActivity  extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            Thread.sleep(1000);
            Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
            startActivity(intent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
