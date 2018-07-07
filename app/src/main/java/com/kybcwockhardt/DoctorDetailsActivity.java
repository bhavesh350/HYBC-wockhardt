package com.kybcwockhardt;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DoctorDetailsActivity extends CustomActivity {


    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Doctor Name");
        setupUiElements();
    }

    private void setupUiElements() {

    }



    private Context getContext() {
        return DoctorDetailsActivity.this;
    }


}
