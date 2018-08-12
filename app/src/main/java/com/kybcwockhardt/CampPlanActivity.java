package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.kybcwockhardt.application.MyApp;

public class CampPlanActivity extends CustomActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_plan);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.camp_plan));
        setupUiElements();

    }

    private void setupUiElements() {
        setTouchNClick(R.id.btn_submit_plan);
        setTouchNClick(R.id.btn_my_camps);
        setTouchNClick(R.id.btn_history);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_submit_plan) {
            startActivity(new Intent(getContext(), CreatePlanActivity.class));
        } else if (v.getId() == R.id.btn_my_camps) {
            startActivity(new Intent(getContext(), MyCampsActivity.class));
        } else if (v.getId() == R.id.btn_history) {
            startActivity(new Intent(getContext(), CampHistoryTMActivity.class));
        }
    }

    private Context getContext() {
        return CampPlanActivity.this;
    }


}
