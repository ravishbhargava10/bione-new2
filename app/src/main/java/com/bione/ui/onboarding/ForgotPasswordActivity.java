package com.bione.ui.onboarding;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;

public class ForgotPasswordActivity extends BaseActivity {

    private AppCompatEditText etEmail;
    private AppCompatTextView tvSave;
    private AppCompatImageView ivClose;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
        tvSave = findViewById(R.id.tvSave);
        ivClose = findViewById(R.id.ivClose);

        ivClose.setOnClickListener(this);
        tvSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivClose:
                finish();
                break;

            case R.id.tvSave:
                if (validEmailOrNumber(etEmail.getText().toString())) {
                    //is number otp verification

                } else {
                    // is email email verification
                }
                break;
        }
    }

    private boolean validEmailOrNumber(final String str) {
        Long number = 0L;
        try {
            number = Long.parseLong(str);
            Log.d("true", "----------" + number);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("false", "----------" + number);
            return false;
        }
    }


}
