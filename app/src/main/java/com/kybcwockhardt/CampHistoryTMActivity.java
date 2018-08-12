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
import android.widget.TextView;

import com.google.gson.Gson;
import com.kybcwockhardt.adapters.CampHistoryTMAdapter;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.models.Camp;
import com.loopj.android.http.RequestParams;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;


public class CampHistoryTMActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private Toolbar toolbar;
    public TextView select_month;
    private RecyclerView rl_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);

        setContentView(R.layout.activity_camp_hostory_zsm);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.camp_history));
        setupUiElements();

    }

    private void setupUiElements() {
        rl_list = findViewById(R.id.rl_list);
        rl_list.setLayoutManager(new LinearLayoutManager(getContext()));

        select_month = findViewById(R.id.select_month);
        select_month.setText("JUNE, 2018");
        setTouchNClick(R.id.select_month);

        RequestParams p = new RequestParams();
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        int year = c.get(Calendar.YEAR);
        p.put("month", month);
        p.put("year", year);
        select_month.setText(getMonth(month-1) + ", " + year);
        p.put("user_id", MyApp.getApplication().readUser().getData().getId());
        postCall(getContext(), BASE_URL + "camp-history-for-tm", p, "Loading...", 1);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == select_month) {
            Calendar c = Calendar.getInstance();
            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(), new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {
                    Log.d("Selected", "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);

                    RequestParams p = new RequestParams();
                    p.put("month", (selectedMonth + 1));
                    p.put("year", selectedYear);
                    p.put("user_id", MyApp.getApplication().readUser().getData().getId());
                    postCall(getContext(), BASE_URL + "camp-history-for-tm", p, "Loading...", 1);
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
        return CampHistoryTMActivity.this;
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (o.optBoolean("status")) {
            Camp h = new Gson().fromJson(o.toString(), Camp.class);
            CampHistoryTMAdapter adapter = new CampHistoryTMAdapter(getContext(), h.getData(), select_month.getText().toString());
            rl_list.setAdapter(adapter);
        } else {
            MyApp.popMessage("Message", "No camp history found for the selected month.", getContext());
            List<Camp.Data> list = new ArrayList<>();
            CampHistoryTMAdapter adapter = new CampHistoryTMAdapter(getContext(), list, select_month.getText().toString());
            rl_list.setAdapter(adapter);
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
