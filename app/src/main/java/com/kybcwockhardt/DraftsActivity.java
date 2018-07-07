package com.kybcwockhardt;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kybcwockhardt.adapters.DraftsAdapter;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Camp;
import com.kybcwockhardt.models.Doctor;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class DraftsActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private Toolbar toolbar;
    private RecyclerView rv_list;
    private Camp.Data campData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts);
        toolbar = findViewById(R.id.toolbar);
        setResponseListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.drafts));

        campData = SingleInstance.getInstance().getSelectedCamp();

        setupUiElements();


    }

    private void setupUiElements() {
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        DraftsAdapter adapter = new DraftsAdapter(getContext(), campData.getPatients());
        rv_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }

    private Context getContext() {
        return DraftsActivity.this;
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (o.optBoolean("status")) {
            MyApp.popMessage("Success", "Submitted Successfully", getContext());
        } else {
            MyApp.popMessage("Error", o.optJSONArray("data").optString(0), getContext());
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

    public void callApiPrescribed(int patientId, String value) {
        RequestParams p = new RequestParams();
        p.put("patient_id", patientId);
        p.put("prescribed", value);
        postCall(getContext(), BASE_URL + "create-prescribed", p, "Please wait...", 1);
//        p.put("not_prescribed", );
    }

    public void callApiNotPrescribed(int patientId, String value) {
        RequestParams p = new RequestParams();
        p.put("patient_id", patientId);
        p.put("not_prescribed", value);
        postCall(getContext(), BASE_URL + "create-prescribed", p, "Please wait...", 1);
//        p.put("not_prescribed", );
    }
}
