package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.kybcwockhardt.application.MyApp;

public class CampExecutionClickActivity extends CustomActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_execution_click);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.camp_execution));
        setupUiElements();

    }

    private void setupUiElements() {
        setTouchNClick(R.id.btn_new_patient);
        setTouchNClick(R.id.btn_history);
        setTouchNClick(R.id.btn_draft);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_history) {
            startActivity(new Intent(getContext(), CampHistoryDetailsUsersActivity.class));
        } else if (v.getId() == R.id.btn_new_patient) {
            startActivity(new Intent(getContext(), AddNewPatientActivity.class));
        } else if (v.getId() == R.id.btn_draft) {
            startActivity(new Intent(getContext(), DraftsActivity.class));
        }
    }

    private Context getContext() {
        return CampExecutionClickActivity.this;
    }


}
