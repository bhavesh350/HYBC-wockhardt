package com.kybcwockhardt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kybcwockhardt.application.AppConstants;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Doctor;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class AddDoctorActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private AutoCompleteTextView edt_name;
    private EditText edt_msl_code;
    private EditText edt_mobile;
    private EditText edt_speciality;
    private EditText edt_city;
    private Toolbar toolbar;
    private Doctor.Data selectedDoctor = null;
    private TextView txt_clear_data;
    private Button btn_submit;
    private Button btn_update;
    private View view_line;
    private List<Doctor.Data> dList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_add_doctor);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Add Doctor");
        setupUiElements();

        RequestParams p = new RequestParams();
        p.put("user_id", MyApp.getApplication().readUser().getData().getId());
        postCall(getContext(), BASE_URL + "get-all-doctors", p, "", 1);
    }

    private String names[];

    private void setupUiElements() {
        btn_submit = findViewById(R.id.btn_submit);
        view_line = findViewById(R.id.view_line);
        btn_update = findViewById(R.id.btn_update);
        txt_clear_data = findViewById(R.id.txt_clear_data);
        edt_name = findViewById(R.id.edt_name);
        edt_name.setThreshold(1);
        edt_msl_code = findViewById(R.id.edt_msl_code);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_speciality = findViewById(R.id.edt_speciality);
        edt_city = findViewById(R.id.edt_city);

        setTouchNClick(R.id.btn_submit);
        setTouchNClick(R.id.btn_update);
        setTouchNClick(R.id.txt_clear_data);

        dList = MyApp.getApplication().readDoctors();
        names = new String[dList.size()];
        for (int i = 0; i < dList.size(); i++) {
            names[i] = dList.get(i).getName() + " (" + dList.get(i).getMsl_code() + ")";
        }
        if (dList.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.text_spinner, names);
            edt_name.setAdapter(adapter);
            edt_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    String selected = (String) parent.getItemAtPosition(pos);
                    int position = Arrays.asList(names).indexOf(selected);
                    edt_city.setText(dList.get(position).getCity());
                    edt_mobile.setText(dList.get(position).getMobile());
                    edt_msl_code.setText(dList.get(position).getMsl_code());
                    edt_name.setText(dList.get(position).getName());
                    edt_speciality.setText(dList.get(position).getSpeciality());
                    isFromSaved = true;
                    selectedDoctor = dList.get(position);
                    btn_update.setVisibility(View.VISIBLE);
                    view_line.setVisibility(View.VISIBLE);
                }
            });

        }

    }

    private boolean isFromSaved = false;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_submit) {
            if (edt_name.getText().toString().isEmpty()) {
                edt_name.setError("Enter doctor name");
                return;
            }
            if (edt_msl_code.getText().toString().isEmpty()) {
                edt_msl_code.setError("Enter MSL code");
                return;
            }
            if (edt_mobile.getText().toString().length() < 10) {
                edt_mobile.setError("Enter a valid mobile number");
                return;
            }
            if (edt_mobile.getText().toString().contains(".") || edt_mobile.getText().toString().contains("+")) {
                edt_mobile.setError("Mobile number is not valid");
                return;
            }
            if (edt_speciality.getText().toString().isEmpty()) {
                edt_speciality.setError("Speciality cannot be empty");
                return;
            }
            if (edt_city.getText().toString().isEmpty()) {
                edt_city.setError("Enter City");
                return;
            }
            boolean isMslMobileNotAvail = false;
            for (int i = 0; i < dList.size(); i++) {
                if (edt_mobile.getText().toString().equals(dList.get(i).getMobile())) {
                    isMslMobileNotAvail = true;
                }

                if (edt_msl_code.getText().toString().equals(dList.get(i).getMsl_code())) {
                    isMslMobileNotAvail = true;
                }
            }

            if (isMslMobileNotAvail) {
                if (isFromSaved) {
                    SingleInstance.getInstance().setSelectedDoctor(selectedDoctor);
                    finish();
                    return;
                }
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setTitle("Wockhardt Alert").setMessage("You are going to edit existing doctor information, do you want to continue?")
                        .setPositiveButton("Edit & proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                RequestParams p = new RequestParams();
                                p.put("user_id", MyApp.getApplication().readUser().getData().getId());
                                p.put("name", edt_name.getText().toString());
                                p.put("msl_code", edt_msl_code.getText().toString());
                                p.put("mobile", edt_mobile.getText().toString());
                                p.put("speciality", edt_speciality.getText().toString());
                                p.put("city", edt_city.getText().toString());

                                postCall(getContext(), AppConstants.BASE_URL + "create-doctor", p, "Creating doctor...", 2);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                return;
            }
            if (isFromSaved) {
                SingleInstance.getInstance().setSelectedDoctor(selectedDoctor);
                finish();
            } else {
                RequestParams p = new RequestParams();
                p.put("user_id", MyApp.getApplication().readUser().getData().getId());
                p.put("name", edt_name.getText().toString());
                p.put("msl_code", edt_msl_code.getText().toString());
                p.put("mobile", edt_mobile.getText().toString());
                p.put("speciality", edt_speciality.getText().toString());
                p.put("city", edt_city.getText().toString());

                postCall(getContext(), AppConstants.BASE_URL + "create-doctor", p, "Creating doctor...", 2);
            }
        } else if (v == txt_clear_data) {
            edt_city.setText("");
            edt_speciality.setText("");
            edt_mobile.setText("");
            edt_msl_code.setText("");
            edt_name.setText("");
            isFromSaved = false;
            btn_update.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
        } else if (v == btn_update) {
            RequestParams p = new RequestParams();
            p.put("user_id", MyApp.getApplication().readUser().getData().getId());
            p.put("name", edt_name.getText().toString());
            p.put("msl_code", edt_msl_code.getText().toString());
            p.put("mobile", edt_mobile.getText().toString());
            p.put("speciality", edt_speciality.getText().toString());
            p.put("city", edt_city.getText().toString());

            postCall(getContext(), AppConstants.BASE_URL + "create-doctor", p, "Creating doctor...", 2);
        }
    }


    private Context getContext() {
        return AddDoctorActivity.this;
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1 && o.optBoolean("status")) {
            Doctor d = new Gson().fromJson(o.toString(), Doctor.class);
            MyApp.getApplication().writeDoctors(d.getData());

            dList = MyApp.getApplication().readDoctors();
            names = new String[dList.size()];
            for (int i = 0; i < dList.size(); i++) {
                names[i] = dList.get(i).getName();
            }
            if (dList.size() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.text_spinner, names);
                edt_name.setAdapter(adapter);
                edt_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        String selected = (String) parent.getItemAtPosition(pos);
                        int position = Arrays.asList(names).indexOf(selected);
                        edt_city.setText(dList.get(position).getCity());
                        edt_mobile.setText(dList.get(position).getMobile());
                        edt_msl_code.setText(dList.get(position).getMsl_code());
                        edt_name.setText(dList.get(position).getName());
                        edt_speciality.setText(dList.get(position).getSpeciality());
                        isFromSaved = true;
                        selectedDoctor = dList.get(position);
                        btn_update.setVisibility(View.VISIBLE);
                        view.setVisibility(View.VISIBLE);
                    }
                });

            }
        } else if (callNumber == 2) {
            if (o.optBoolean("status")) {
                Doctor d = new Gson().fromJson(o.toString(), Doctor.class);
                MyApp.getApplication().writeDoctors(d.getData());
            } else {
                MyApp.popMessage("Error", "MSL code already exists", getContext());
            }
            List<Doctor.Data> list = MyApp.getApplication().readDoctors();
            Doctor.Data d = new Doctor().new Data();
            d.setCity(edt_city.getText().toString());
            d.setMobile(edt_mobile.getText().toString());
            d.setMsl_code(edt_msl_code.getText().toString());
            d.setSpeciality(edt_speciality.getText().toString());
            d.setName(edt_name.getText().toString());


            SingleInstance.getInstance().setSelectedDoctor(d);
            finish();
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
        MyApp.popMessage("Error!", error, getContext());
    }
}
