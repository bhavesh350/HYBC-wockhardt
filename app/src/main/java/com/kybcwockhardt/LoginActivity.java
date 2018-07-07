package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.kybcwockhardt.application.AppConstants;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.models.User;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private EditText edt_emp_id;
    private EditText edt_password;
    private Spinner spinner_designation;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setResponseListener(this);
        setupUiElements();
        setClick(R.id.btn_login);
        setClick(R.id.txt_generate_password);

    }

    private void setupUiElements() {
        edt_emp_id = findViewById(R.id.edt_emp_id);
        edt_password = findViewById(R.id.edt_password);
        spinner_designation = findViewById(R.id.spinner_designation);
        btn_login = findViewById(R.id.btn_login);

        List<String> categories = new ArrayList<>();
        categories.add("TM");
        categories.add("RM");
        categories.add("ZSM");
        categories.add("SM");
        categories.add("NSM");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.text_spinner);

        // attaching data adapter to spinner
        spinner_designation.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btn_login) {

            if (edt_emp_id.getText().toString().isEmpty()) {
                edt_emp_id.setError(getString(R.string.err_emp_id));
                return;
            }
            if (edt_password.getText().toString().isEmpty()) {
                edt_password.setError(getString(R.string.err_password));
            }

            RequestParams p = new RequestParams();
            p.put("emp_no", edt_emp_id.getText().toString());
            p.put("password", edt_password.getText().toString());
            p.put("device_type", "Android");
            p.put("device_token", MyApp.getSharedPrefString(AppConstants.DEVICE_TOKEN));

            postCall(getContext(), AppConstants.BASE_URL + "login", p, "Logging you in...", 1);
        } else if (v.getId() == R.id.txt_generate_password) {
            startActivity(new Intent(getContext(), GeneratePasswordActivity.class));
        }
    }

    private Context getContext() {
        return LoginActivity.this;
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optBoolean("status")) {

                User user = new Gson().fromJson(o.toString(), User.class);
                if (user.getData().getDesignation().equals(spinner_designation.getSelectedItem().toString())) {
                    startActivity(new Intent(getContext(), MainActivity.class).putExtra("UserType",
                            spinner_designation.getSelectedItemPosition()));
                    MyApp.getApplication().writeUser(user);
                    MyApp.setSharedPrefString(AppConstants.EMPLOYEE_ID, user.getData().getEmp_no() + "");
                    MyApp.setStatus(AppConstants.IS_LOGIN, true);
                    finish();
                } else {
                    MyApp.popMessage("Error!", "You are registered as a "
                            + user.getData().getDesignation() + " But you are trying to login as a " +
                            spinner_designation.getSelectedItem().toString(), getContext());
                }

            } else {
                MyApp.popMessage("Error", o.optJSONArray("data").optString(0), getContext());
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
        MyApp.popMessage("Error", error, getContext());
    }
}
