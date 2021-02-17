package com.bione.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.dashboard.ChangePasswordActivity;
import com.bione.utils.Log;
import com.bione.utils.ValidationUtil;
import com.google.android.material.textfield.TextInputLayout;

import static com.bione.utils.AppConstant.PARAM_MOBILE;
import static com.bione.utils.AppConstant.PARAM_OTP;
import static com.bione.utils.AppConstant.PARAM_PASSWORD;
import static com.bione.utils.AppConstant.PARAM_PHONE;
import static com.bione.utils.AppConstant.PARAM_USERNAME;

public class ForgotPasswordActivity extends BaseActivity {

    private AppCompatEditText etEmail;
    private AppCompatEditText etOtp;
    private AppCompatTextView tvSend;
    private AppCompatImageView ivClose;
    private TextInputLayout layoutOtp;
    private TextInputLayout layoutEmail;

    private int condition = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();

    }

    private void init() {
        etEmail = findViewById(R.id.etEmail);
        etOtp = findViewById(R.id.etOtp);
        tvSend = findViewById(R.id.tvSend);
        ivClose = findViewById(R.id.ivClose);
        layoutOtp = findViewById(R.id.layoutOtp);
        layoutEmail = findViewById(R.id.layoutEmail);

        ivClose.setOnClickListener(this);
        tvSend.setOnClickListener(this);

        condition = 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivClose:
                finish();
                break;

            case R.id.tvSend:
                if (condition == 0) {
                    if (validNumber(etEmail.getText().toString())) {
                        //is number otp verification
                        tvSend.setText("Verify");
                        layoutOtp.setVisibility(View.VISIBLE);

                    } else {
                        // is email email verification
                        if (ValidationUtil.checkEmail(etEmail.getText().toString())) {
//                            Intent intent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
//                            intent.putExtra("fromForgot",true);
//                            startActivity(intent);
                            finish();
                        } else {
                            condition = 0;
                            showErrorMessage("Please enter valid email");
                        }
                    }
                } else {
                    if (condition == 1) {
                        if (etEmail.getText().toString().isEmpty()) {
                            showErrorMessage("Please enter phone number");
                        } else {
                            if (etOtp.getText().toString().isEmpty()) {
                                showErrorMessage("Please enter OTP");
                            } else {
                                Intent intent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
                                intent.putExtra("fromForgot", true);
                                intent.putExtra("mobile", etEmail.getText().toString());
                                intent.putExtra("otp", etOtp.getText().toString());
                                startActivity(intent);
                            }
                        }
                    }
                }
                break;
        }
    }

    private boolean validNumber(final String str) {
        Long number = 0L;
        try {
            number = Long.parseLong(str);
            Log.d("true", "----------" + number);
            condition = 1;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            condition = 3;
            Log.d("false", "----------" + number);
            return false;
        }
    }

    private void forgotSendOtp() {
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_MOBILE, etEmail.getText().toString())
                .build();

        Log.d("code ", "map :: " + commonParams.getMap());
        RestClient.getApiInterface().forgotSendOtp(commonParams.getMap()).enqueue(new ResponseResolver<String>() {
            @Override
            public void onSuccess(String s) {

                Log.d("String ", "Value :: " + s);

            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
                showErrorMessage(error.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
            }
        });
    }

    private void forgotVerifyOtp() {
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_MOBILE, etEmail.getText().toString())
                .add(PARAM_OTP, etEmail.getText().toString())
                .build();

        Log.d("code ", "map :: " + commonParams.getMap());
        RestClient.getApiInterface().forgotVerifyOtp(commonParams.getMap()).enqueue(new ResponseResolver<Boolean>() {
            @Override
            public void onSuccess(Boolean s) {

                Log.d("Boolean ", "Value :: " + s);

            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
                showErrorMessage(error.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
            }
        });
    }


}
