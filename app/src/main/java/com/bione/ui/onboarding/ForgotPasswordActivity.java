package com.bione.ui.onboarding;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;

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
import com.hbb20.CountryCodePicker;

import static com.bione.utils.AppConstant.PARAM_MOBILE;
import static com.bione.utils.AppConstant.PARAM_OTP;
import static com.bione.utils.AppConstant.PARAM_PASSWORD;
import static com.bione.utils.AppConstant.PARAM_PHONE;
import static com.bione.utils.AppConstant.PARAM_USERNAME;

public class ForgotPasswordActivity extends BaseActivity {

    private AppCompatEditText etPhone;
    private AppCompatEditText etEmail;
    private AppCompatEditText etOtp;
    private AppCompatTextView tvSend;
    private AppCompatTextView tvResetType;
    private AppCompatImageView ivClose;
    private TextInputLayout layoutOtp;
    private TextInputLayout layoutEmail;
    private LinearLayout llCountryCode;

    private CountryCodePicker ccp;
    private int condition = 0;
//    private boolean resetMobile = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();

    }

    private void init() {
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etOtp = findViewById(R.id.etOtp);
        tvSend = findViewById(R.id.tvSend);
        ivClose = findViewById(R.id.ivClose);
        layoutOtp = findViewById(R.id.layoutOtp);
        layoutEmail = findViewById(R.id.layoutEmail);
        tvResetType = findViewById(R.id.tvResetType);
        llCountryCode = findViewById(R.id.llCountryCode);
        ccp = findViewById(R.id.ccp);

        tvResetType.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        ccp.setOnClickListener(this);

        llCountryCode.setVisibility(View.GONE);
        layoutEmail.setVisibility(View.VISIBLE);
        tvResetType.setText("Reset with phone");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvResetType:
                if (condition == 0) {
                    llCountryCode.setVisibility(View.VISIBLE);
                    layoutEmail.setVisibility(View.GONE);
                    tvResetType.setText("Reset with email");
                    condition = 1;
//                    resetMobile = false;
                } else {
                    llCountryCode.setVisibility(View.VISIBLE);
                    layoutEmail.setVisibility(View.GONE);
                    tvResetType.setText("Reset with phone");
                    condition = 0;
                }
                break;

            case R.id.ivClose:
                finish();
                break;

            case R.id.tvSend:
//                if (resetMobile) { // mobile number checks then otp check then api
//                    if (condition == 0) {
//                        //is number otp verification API hit
//                        tvSend.setText("Verify");
//                        layoutOtp.setVisibility(View.VISIBLE);
//
//                    } else if (condition == 1) {
//                        if (etPhone.getText().toString().isEmpty()) {
//                            showErrorMessage("Please enter phone number");
//                        } else {
//                            if (etOtp.getText().toString().isEmpty()) {
//                                showErrorMessage("Please enter OTP");
//                            } else {
//                                Intent intent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
//                                intent.putExtra("fromForgot", true);
//                                intent.putExtra("mobile", ccp.getSelectedCountryCode() + etPhone.getText().toString());
//                                intent.putExtra("otp", etOtp.getText().toString());
//                                startActivity(intent);
//                            }
//                        }
//                    }
//                } else { // email validation then api
//
//                    // is email email verification
//                    if (ValidationUtil.checkEmail(etEmail.getText().toString())) {
//                        // API hit email link sent
//                        openDialog();
//                    } else {
//                        showErrorMessage("Please enter valid email");
//                    }
//
//                }

                if (condition == 0) {

                    // is email email verification
                    if (ValidationUtil.checkEmail(etEmail.getText().toString())) {
                        openDialog();
                    } else {
                        condition = 0;
                        showErrorMessage("Please enter valid email");
                    }

                } else if (condition == 1) {
                    //number valid
                    if (!etPhone.getText().toString().isEmpty()) {
                        //is number otp verification
                        tvSend.setText("Verify");
                        layoutOtp.setVisibility(View.VISIBLE);
                        condition = 2;

                    } else {
                        showErrorMessage("Please enter phone number");
                    }
                } else if (condition == 2) {
                    // otp verify
                    if (etPhone.getText().toString().isEmpty()) {
                        showErrorMessage("Please enter phone number");
                    } else {
                        if (etOtp.getText().toString().isEmpty()) {
                            showErrorMessage("Please enter OTP");
                        } else {
                            Intent intent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
                            intent.putExtra("fromForgot", true);
                            intent.putExtra("mobile", etPhone.getText().toString());
                            intent.putExtra("otp", etOtp.getText().toString());
                            startActivity(intent);
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


    private void openDialog() {
// custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_success_reset);
//        dialog.getWindow().setBackgroundDrawable(null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Title...");
        // set the custom dialog components - text, image and button

        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);

        // if button is clicked, close the custom dialog
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

}
