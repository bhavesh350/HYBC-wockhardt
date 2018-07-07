package com.kybcwockhardt;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kybcwockhardt.adapters.CampHistoryDetailsUsersAdapter;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Camp;

public class CampHistoryDetailsUsersActivity extends CustomActivity {

    private Toolbar toolbar;
    private RecyclerView rv_list;
    private TextView txt_doctor;
    private Camp.Data campData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_history_details_users);
        campData = SingleInstance.getInstance().getSelectedCamp();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("History");
        setupUiElements();

    }

    private void setupUiElements() {
        rv_list = findViewById(R.id.rv_list);
        txt_doctor = findViewById(R.id.txt_doctor);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
//        rv_list.setNestedScrollingEnabled(false);

        txt_doctor.setText("Dr. "+campData.getDoctor().getName() + " (" + campData.getDoctor().getMsl_code() + ")"
                + ((campData.getPatients().size() > 0) ? "" : "\n\n\n No patient created yet"));

        CampHistoryDetailsUsersAdapter adapter = new CampHistoryDetailsUsersAdapter(getContext(),
                SingleInstance.getInstance().getSelectedCamp().getPatients());
        rv_list.setAdapter(adapter);
//        setTouchNClick(R.id.btn_share);
    }

    @Override
    protected void onResume() {
        super.onResume();

        CampHistoryDetailsUsersAdapter adapter = new CampHistoryDetailsUsersAdapter(getContext(),
                SingleInstance.getInstance().getSelectedCamp().getPatients());
        rv_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
//        if (v.getId() == R.id.btn_share) {
//            MyApp.showMassage(getContext(), "An csv file will be generated based on the data, and that will be shared.");
//        }
    }

    private Context getContext() {
        return CampHistoryDetailsUsersActivity.this;
    }


}
