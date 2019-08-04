package com.example.lunchrun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashActivity  extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            Thread.sleep(1000);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
