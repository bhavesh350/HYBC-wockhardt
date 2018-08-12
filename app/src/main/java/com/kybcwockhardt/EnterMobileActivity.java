package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kybcwockhardt.application.MyApp;


public class EnterMobileActivity extends CustomActivity {
    private TextView tv_welcome;
    private EditText edt_phone_no;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Mobile Verification");


        setupUiElement();
    }

    private void setupUiElement() {

        setTouchNClick(R.id.btn_login);
        edt_phone_no = findViewById(R.id.edt_phone_no);
        tv_welcome = findViewById(R.id.tv_welcome);
        tv_welcome.setText("Welcome User" + "\nEnter your Mobile Number");

//        Shader textShader = new LinearGradient(0, 0, 0, 50,
//                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
//                new float[]{0, 1}, Shader.TileMode.CLAMP);
//        tv_btn_signin.getPaint().setShader(textShader);
    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_login) {
            if (TextUtils.isEmpty(edt_phone_no.getText().toString())) {
                edt_phone_no.setError("Enter Your Mobile Number");
                return;
            }
            if (edt_phone_no.getText().toString().length() < 10) {
                edt_phone_no.setError("Enter a valid mobile number");
                return;
            }

            Intent intent = new Intent(getContext(), VerifyMobileActivity.class);
            intent.putExtra("phone", edt_phone_no.getText().toString());
            startActivity(intent);
            finish();
        }

    }


    private Context getContext() {
        return EnterMobileActivity.this;
    }
}
