package com.bione.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.walkthrough.WalkActivity;


public class SplashActivity extends BaseActivity {


    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        call();
    }

    private void call() {
//        showLoading();
        handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
//                adminTokenAPI();
                Intent intent = new Intent(SplashActivity.this, WalkActivity.class);
                startActivity(intent);
            }
        };
        handler.postDelayed(r, 1000);
    }

    @Override
    public void onClick(View view) {

    }
}
