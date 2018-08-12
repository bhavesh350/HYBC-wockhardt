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
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.kybcwockhardt.application.MyApp;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Patient;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class AddNewPatientActivity extends CustomActivity implements CustomActivity.ResponseCallback{

    private Toolbar toolbar;
    private EditText edt_dob, edt_height, edt_weight;
    private EditText edt_patient_name, edt_mobile, edt_abdo;
    private RadioGroup radio_group;
    private String dob = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_add_new_patient);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.new_patient));
        setupUiElements();
//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            MyApp.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//        if (Build.VERSION.SDK_INT >= 21) {
//            MyApp.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
    }


    private boolean isMale = true;

    private void setupUiElements() {
        setTouchNClick(R.id.btn_view_drafts);
        setTouchNClick(R.id.btn_submit);
        setTouchNClick(R.id.btn_date);
        edt_dob = findViewById(R.id.edt_dob);
        edt_height = findViewById(R.id.edt_height);
        edt_abdo = findViewById(R.id.edt_abdo);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_weight = findViewById(R.id.edt_weight);
        edt_patient_name = findViewById(R.id.edt_patient_name);
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(edt_dob, smf);
        edt_dob.addTextChangedListener(mtw);

        radio_group = findViewById(R.id.radio_group);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_male) {
                    isMale = true;
                } else if (checkedId == R.id.radio_female) {
                    isMale = false;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_date) {
            dateDialog();
        }else if (v.getId() == R.id.txt_clear_data) {
            edt_abdo.setText("");
            edt_weight.setText("");
            edt_height.setText("");
            edt_dob.setText("");
            edt_mobile.setText("");
            edt_patient_name.setText("");
        }  else if (v.getId() == R.id.btn_submit) {

            if (edt_patient_name.getText().toString().isEmpty()) {
                edt_patient_name.setError("Enter patient name");
                return;
            }

            if (edt_mobile.getText().toString().isEmpty() && edt_mobile.getText().length() != 10) {
                edt_mobile.setError("Enter a valid mobile number");
                return;
            }

            if(edt_mobile.getText().toString().contains(".") || edt_mobile.getText().toString().contains("+")){
                edt_mobile.setError("Mobile number is not valid");
                return;
            }

            if (edt_dob.getText().toString().isEmpty() && edt_dob.getText().toString().length() != 10) {
                edt_dob.setError("Enter a valid dob");
                return;
            }

            if (edt_height.getText().toString().isEmpty()) {
                edt_height.setError("Enter height in feet decimal");
                return;
            }
            if (Float.parseFloat(edt_height.getText().toString()) > 8) {
                MyApp.popMessage("Error", "You entered height '" + edt_height.getText().toString() + " " +
                        "feet' is abnormal.", getContext());
            }
            if (edt_weight.getText().toString().isEmpty()) {
                edt_height.setError("Enter weight in kg");
                return;
            }
            if (Float.parseFloat(edt_weight.getText().toString()) > 250) {
                MyApp.popMessage("Error", "You entered weight '" + edt_weight.getText().toString() + " " +
                        "KG' is abnormal.", getContext());
            }
            if (edt_abdo.getText().toString().isEmpty()) {
                edt_abdo.setError("Enter abdominal circumference");
                return;
            }
            if (Float.parseFloat(edt_abdo.getText().toString()) > 10) {
                MyApp.popMessage("Error", "You entered abdominal circumference '" +
                        edt_abdo.getText().toString() + " " +
                        "CM' is abnormal.", getContext());
            }
            String maleFemale = isMale ? "Male" : "Female";
            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
            b.setTitle("Confirm Patient").setMessage("Are you sure with following details of the patient\n\n"
                    + "Name : " + edt_patient_name.getText().toString() + " (" + maleFemale + ")\n"
                    + "Mob No : " + edt_mobile.getText().toString() + "\nDOB : " + edt_dob.getText().toString() + "\n" +
                    "Height : " + edt_height.getText().toString() + " Feet\n" +
                    "Weight : " + edt_weight.getText().toString() + " KG\n" +
                    "Abdominal Cir : " + edt_abdo.getText().toString() + " CM")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            RequestParams p = new RequestParams();
                            p.put("camp_id", SingleInstance.getInstance().getSelectedCamp().getId());
                            p.put("name", edt_patient_name.getText().toString());
                            p.put("sex", isMale ? "m" : "f");
                            p.put("mobile", edt_mobile.getText().toString());
                            p.put("dob", createCampData);
                            p.put("height", edt_height.getText().toString());
                            p.put("weight", edt_weight.getText().toString());
                            p.put("abdominal_circumference", edt_abdo.getText().toString());
                            postCall(getContext(), BASE_URL + "create-patient", p, "Creating Patient...", 1);
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

//            startActivity(new Intent(getContext(), QuestionnaireActivity.class));
        } else if (v.getId() == R.id.btn_view_drafts) {
            startActivity(new Intent(getContext(), DraftsActivity.class));
        }
    }

    private Context getContext() {
        return AddNewPatientActivity.this;
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
                        edt_dob.setText(parseDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
//                        myYear = year;
//                        myMonth = monthOfYear;
//                        myDay = dayOfMonth;
//                        date_to_be_sent = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public String parseDate(String time) {
        Log.e("Date", "parseDateToHHMM: " + time);
        String inputPattern = "d-M-yyyy";
        String outputPattern = "dd/MM/yyyy";
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
            e.printStackTrace();
        }
        return str;
    }
    String createCampData;

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1 && o.optBoolean("status")) {
            MyApp.showMassage(getContext(), "Data saved");
            Patient.Data p = new Gson().fromJson(o.optJSONObject("data").toString(), Patient.Data.class);
            SingleInstance.getInstance().setPatient(p);
            startActivity(new Intent(getContext(), QuestionnaireActivity.class));
            edt_abdo.setText("");
            edt_weight.setText("");
            edt_height.setText("");
            edt_dob.setText("");
            edt_mobile.setText("");
            edt_patient_name.setText("");
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
