package com.bione.ui.onboarding;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.MainActivity;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.CustomTypefaceSpan;

public class LoginActivity extends BaseActivity {

    private LinearLayout llPhone;
    private LinearLayout llEmail;
    private AppCompatTextView tvLoginType;
    private AppCompatTextView tvHead;
    private AppCompatTextView tvLogin;
    private AppCompatTextView tvCreateAccount;
    private AppCompatTextView tvForgotPassword;
    private int loginType = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        llPhone = findViewById(R.id.llPhone);
        llEmail = findViewById(R.id.llEmail);

        tvHead = findViewById(R.id.tvHead);


        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder(tvHead.getText().toString());
        SS.setSpan(new CustomTypefaceSpan("", font), 0, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        SS.setSpan(new CustomTypefaceSpan("", font2), 4, tvHead.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvHead.setText(SS);

        tvLoginType = findViewById(R.id.tvLoginType);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);
        tvLoginType.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvLoginType:
                if (loginType == 0) {
                    loginType = 1;
                    setLoginType(llEmail, llPhone);
                    tvLoginType.setText(R.string.login_with_mobile);
                } else {
                    loginType = 0;
                    setLoginType(llPhone, llEmail);
                    tvLoginType.setText(R.string.login_with_email);
                }
                break;

            case R.id.tvCreateAccount:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.tvForgotPassword:
                Intent intent2 = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent2);
                break;

            case R.id.tvLogin:
                Intent intent3 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent3);
                break;

        }
    }

    private void setLoginType(LinearLayout visible, LinearLayout gone) {
        visible.setVisibility(View.VISIBLE);
        gone.setVisibility(View.GONE);

    }
}
