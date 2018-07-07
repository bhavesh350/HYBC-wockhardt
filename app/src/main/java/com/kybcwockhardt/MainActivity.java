package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kybcwockhardt.application.AppConstants;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.models.Doctor;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;
import static com.kybcwockhardt.application.AppConstants.EMPLOYEE_ID;

public class MainActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private int userType;
    private TextView txt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setResponseListener(this);
        userType = getIntent().getIntExtra("UserType", 0);

        RequestParams p = new RequestParams();
        p.put("emp_no", MyApp.getSharedPrefString(EMPLOYEE_ID));
        postCall(getContext(), BASE_URL + "get-all-doctors", p, "", 1);

        if (userType == 1) {
            startActivity(new Intent(getContext(), RmMainActivity.class));
            finish();
        } else if (userType == 2 || userType == 3 || userType == 4) {
            startActivity(new Intent(getContext(), ZsmSmMainActivity.class));
            finish();
        }

        setupUiElements();
    }

    private void setupUiElements() {
        txt_name = findViewById(R.id.txt_name);
        txt_name.setText("Logged in as : " + MyApp.getApplication().readUser().getData().getName());
        setClick(R.id.btn_my_doctors);
        setClick(R.id.btn_camp_plan);
        setClick(R.id.btn_camp_execution);
        setClick(R.id.btn_notification);
        setClick(R.id.btn_leaderboard);
        setClick(R.id.txt_logout);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_my_doctors) {
            startActivity(new Intent(getContext(), MyDoctorsActivity.class));
        } else if (v.getId() == R.id.btn_camp_plan) {
            startActivity(new Intent(getContext(), CampPlanActivity.class));
        } else if (v.getId() == R.id.btn_camp_execution) {
            startActivity(new Intent(getContext(), CampExecutionActivity.class));
        } else if (v.getId() == R.id.btn_notification) {
            startActivity(new Intent(getContext(), NotificationsActivity.class));
        } else if (v.getId() == R.id.btn_leaderboard) {
            startActivity(new Intent(getContext(), LeaderboardActivity.class));
        } else if (v.getId() == R.id.txt_logout) {
            MyApp.setStatus(AppConstants.IS_LOGIN, false);
            startActivity(new Intent(getContext(), LoginActivity.class));
            finishAffinity();
        }


    }

    private Context getContext() {
        return MainActivity.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optBoolean("status")) {
                Doctor d = new Gson().fromJson(o.toString(), Doctor.class);
                MyApp.getApplication().writeDoctors(d.getData());
            } else {
//                MyApp.popMessage("Error","");
            }
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }
}
