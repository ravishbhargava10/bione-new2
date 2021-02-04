package com.bione.ui.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Base64;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bione.R;
import com.bione.ui.MainActivity;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.walkthrough.WalkActivity;
import com.bione.utils.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SplashActivity extends BaseActivity {


    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d("hash key", " : " + printKeyHash(this));
        call();

    }

    private void call() {
        showLoading();
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

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
}
