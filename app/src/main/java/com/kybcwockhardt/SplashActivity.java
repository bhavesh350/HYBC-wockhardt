package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.kybcwockhardt.application.AppConstants;
import com.kybcwockhardt.application.MyApp;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MyApp.getStatus(AppConstants.IS_LOGIN)) {
                    startActivity(new Intent(getContext(), MainActivity.class).putExtra("UserType", getUserType()));
                    finish();
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    finish();
                }

            }
        }, 1500);
    }

    private int getUserType() {
        int type = 0;
        switch (MyApp.getApplication().readUser().getData().getDesignation()) {
            case "TM":
                type = 0;
                break;
            case "RM":
                type = 1;
                break;
            case "ZSM":
                type = 2;
                break;
            case "SM":
                type = 3;
                break;
            case "NSM":
                type = 4;
                break;
            default:
                type = 0;
                break;
        }
        return type;
    }

    private Context getContext() {
        return SplashActivity.this;
    }
}