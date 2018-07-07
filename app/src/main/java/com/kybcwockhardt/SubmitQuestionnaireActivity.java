package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Patient;

public class SubmitQuestionnaireActivity extends CustomActivity {
    private Toolbar toolbar;
    private TextView txt_score;
    private Patient.Question questionReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionReport = SingleInstance.getInstance().getCurrentQuestionReport();
        setContentView(R.layout.activity_submit_qustionnaires);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.result));
        setupUiElements();
    }

    private void setupUiElements() {
        txt_score = findViewById(R.id.txt_score);
        txt_score.setText("Your JAMRISC score is\n\n" + questionReport.getScore());
        setTouchNClick(R.id.btn_share);
        setTouchNClick(R.id.btn_next_patient);
        setTouchNClick(R.id.btn_finish_camp);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_share) {
            MyApp.shareText(getContext(), "Patient Report", "Some text will be hare based on the generated report.");
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = SingleInstance.getInstance().getPatient().getName() + " has been scanned in a camp with the 'Dr. " +
                    SingleInstance.getInstance().getSelectedCamp().getDoctor().getName() + "' and the Wockhardt JAMRISC score is:\n\n" + questionReport.getScore();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Wockhardt JAMRISC report");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (v.getId() == R.id.btn_next_patient) {
            finish();
        } else if (v.getId() == R.id.btn_finish_camp) {
            startActivity(new Intent(getContext(), MainActivity.class));
            finishAffinity();
        }

    }

    private Context getContext() {
        return SubmitQuestionnaireActivity.this;
    }
}