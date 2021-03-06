package com.kybcwockhardt;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kybcwockhardt.adapters.CampHistoryZSMAdapter;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.models.CampHistoryZsm;
import com.loopj.android.http.RequestParams;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class CampHistoryZSMActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private Toolbar toolbar;
    public TextView select_month;
    private RecyclerView rl_list;
    private boolean isNSM = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        isNSM = getIntent().getBooleanExtra("isSM", false);


        setContentView(R.layout.activity_camp_hostory_zsm);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getDesignationToShow(MyApp.getApplication().readUser().getData().getDesignation()));
        try {
            if (getIntent().getStringExtra("title").length() > 1) {
                getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
            }
        } catch (Exception e) {
        }
        setupUiElements();
    }

    private void setupUiElements() {
        rl_list = findViewById(R.id.rl_list);
        rl_list.setLayoutManager(new LinearLayoutManager(getContext()));

        select_month = findViewById(R.id.select_month);

        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        int year = c.get(Calendar.YEAR);



        if (isNSM)
            select_month.setText(getIntent().getStringExtra("date"));
        else
            select_month.setText(getMonth(month-1) + ", " + year);
        setTouchNClick(R.id.select_month);

        RequestParams p = new RequestParams();

        if (getIntent().getIntExtra("year", 0) == 0) {
            p.put("month", month);
            p.put("year", year);
        } else {
            p.put("month", getIntent().getIntExtra("month", 0));
            p.put("year", getIntent().getIntExtra("year", 0));
        }

        if (!isNSM) {
            if (MyApp.getApplication().readUser().getData().getDesignation().equals("SM")) {
                p.put("emp_id", MyApp.getApplication().readUser().getData().getId());
                postCall(getContext(), BASE_URL + "camp-history-sm", p, "Loading...", 1);
            } else {
                p.put("user_id", MyApp.getApplication().readUser().getData().getId());
                postCall(getContext(), BASE_URL + "camp-history", p, "Loading...", 1);
            }
        } else {
            if (getIntent().getStringExtra("designation").equals("SM")) {
                p.put("emp_id", getIntent().getIntExtra("userId", 0));
                postCall(getContext(), BASE_URL + "camp-history-sm", p, "Loading...", 1);
            } else {
                p.put("user_id", getIntent().getIntExtra("userId", 0));
                postCall(getContext(), BASE_URL + "camp-history", p, "Loading...", 1);
            }
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == select_month) {
            if (isNSM) return;
            Calendar c = Calendar.getInstance();
            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(), new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {
                    Log.d("Selected", "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);

                    RequestParams p = new RequestParams();
                    p.put("month", (selectedMonth + 1));
                    p.put("year", selectedYear);
                    if (MyApp.getApplication().readUser().getData().getDesignation().equals("SM")) {
                        p.put("emp_id", MyApp.getApplication().readUser().getData().getId());
                        postCall(getContext(), BASE_URL + "camp-history-sm", p, "Loading...", 1);
                    } else {
                        p.put("user_id", MyApp.getApplication().readUser().getData().getId());
                        postCall(getContext(), BASE_URL + "camp-history", p, "Loading...", 1);
                    }
                    select_month.setText(getMonth(selectedMonth) + ", " + selectedYear);
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH));

            builder.setActivatedMonth(Calendar.JULY)
                    .setMinYear(2017)
                    .setActivatedYear(2018)
                    .setMaxYear(2030)
                    .setTitle("Select month")
                    .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                        @Override
                        public void onMonthChanged(int selectedMonth) {
                            Log.d("Selected", "Selected month : " + selectedMonth);
                            // Toast.makeText(MainActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                        @Override
                        public void onYearChanged(int selectedYear) {
                            Log.d("Selected", "Selected year : " + selectedYear);
                            // Toast.makeText(MainActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build()
                    .show();
        }
    }

    private String getMonth(int selectedMonth) {
        switch (selectedMonth) {
            case 0:
                return "JAN";
            case 1:
                return "FEB";
            case 2:
                return "MARCH";
            case 3:
                return "APRIL";
            case 4:
                return "MAY";
            case 5:
                return "JUNE";
            case 6:
                return "JULY";
            case 7:
                return "AUG";
            case 8:
                return "SEPT";
            case 9:
                return "OCT";
            case 10:
                return "NOV";
            case 11:
                return "DEC";
            default:
                return "JULY";
        }
    }


    private Context getContext() {
        return CampHistoryZSMActivity.this;
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (o.optBoolean("status")) {
            CampHistoryZsm h = new Gson().fromJson(o.toString(), CampHistoryZsm.class);
            CampHistoryZSMAdapter adapter = new CampHistoryZSMAdapter(getContext(), h.getData().get(0).getChild(), select_month.getText().toString());
            rl_list.setAdapter(adapter);
        } else {
            MyApp.popMessage("Message", "No camp history found for the selected month.", getContext());
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {

    }
    public String getDesignationToShow(String designation) {
        if (designation.equals("NSM")) {
            return "SM History";
        } else if (designation.equals("SM")) {
            return "ZSM History";
        } else if (designation.equals("ZSM")) {
            return "RM History";
        } else if (designation.equals("RM")) {
            return "TM History";
        }
        return designation;
    }
    @Override
    public void onErrorReceived(String error) {
        MyApp.popMessage("Error", error, getContext());
    }
}
