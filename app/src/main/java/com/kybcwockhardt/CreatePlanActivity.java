package com.kybcwockhardt;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Doctor;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class CreatePlanActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private Toolbar toolbar;
    private TextView txt_date;
    private TextView txt_doctor_details;
    private String createCampData = "";
    private EditText edt_patient_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_create_plan);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.create_plan));
        setupUiElements();


    }

    private void setupUiElements() {
        edt_patient_count = findViewById(R.id.edt_patient_count);
        txt_date = findViewById(R.id.txt_date);
        txt_doctor_details = findViewById(R.id.txt_doctor_details);
        setClick(R.id.txt_date);
        setClick(R.id.txt_doctor_details);
        setClick(R.id.btn_submit);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_date) {
            dateDialog();
        } else if (v.getId() == R.id.txt_doctor_details) {
            startActivity(new Intent(getContext(), AddDoctorActivity.class));
        } else if (v.getId() == R.id.btn_submit) {
            if (SingleInstance.getInstance().getSelectedDoctor() == null) {
                MyApp.popMessage("Alert", "Please add a doctor", getContext());
                return;
            }
            if (createCampData.isEmpty()) {
                MyApp.popMessage("Alert", "Please select a date when camp needs to execute.", getContext());
                return;
            }
            if (edt_patient_count.getText().toString().isEmpty()) {
                edt_patient_count.setError("Enter expected number of patients.");
                return;
            }

            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
            b.setTitle("Creating Camp?").setMessage("Are you sure that you want to create a camp with following details?\n" +
                    "\nDoctor\n" + txt_doctor_details.getText().toString() + "\n" +
                    "\nOn Date : " + txt_date.getText().toString() + "\n\nExpected patients : " + edt_patient_count.getText().toString())
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            RequestParams p = new RequestParams();
                            p.put("user_id", MyApp.getApplication().readUser().getData().getId());
                            p.put("msl_code", SingleInstance.getInstance().getSelectedDoctor().getMsl_code());
                            p.put("camp_date", createCampData);
                            p.put("patient_count", edt_patient_count.getText().toString());

                            postCall(getContext(), BASE_URL + "camp-plan", p, "Creating Camp", 1);
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        }
    }

    public void dateDialog() {
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txt_date.setText(parseDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public String parseDate(String time) {
        Log.e("Date", "parseDateToHHMM: " + time);
        String inputPattern = "d-M-yyyy";
        String outputPattern = "d MMM, yyyy";
        String outputPatternServer = "yyyy-MM-dd";//2018-06-18
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        SimpleDateFormat outputFormatServer = new SimpleDateFormat(outputPatternServer);

        Date date;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);

            createCampData = outputFormatServer.format(date);
        } catch (ParseException e) {
            date = null;
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            //task related to sunday
            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
            b.setTitle("Wockhardt Alert!").setMessage("Choosed date is sunday, are you sure to create a camp " +
                    "on sunday?").setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setNegativeButton("Change", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    dateDialog();
                }
            }).create().show();
        } else {
            //tasks related to other days
        }

        return str;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Doctor.Data d = SingleInstance.getInstance().getSelectedDoctor();
        if (d != null) {
            txt_doctor_details.setText(d.getName() + "(" + d.getMsl_code() + ")" + "\n" + d.getMobile());
        }
    }

    private Context getContext() {
        return CreatePlanActivity.this;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SingleInstance.getInstance().setSelectedDoctor(null);
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1 && o.optBoolean("status")) {
            MyApp.popFinishableMessage("Message", "Camp created successfully. " +
                    "You can check status of the camp with My Camps menu option.\nThank you.", CreatePlanActivity.this);
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
        MyApp.popMessage("Error", error, getContext());
    }
}
