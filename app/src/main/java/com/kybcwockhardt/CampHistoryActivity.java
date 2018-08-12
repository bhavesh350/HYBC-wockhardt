package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kybcwockhardt.adapters.MyTeamAdapter;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Camp;
import com.kybcwockhardt.models.MyTeam;
import com.loopj.android.http.RequestParams;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class CampHistoryActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private Toolbar toolbar;
    private TextView select_month;
    private RecyclerView rv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_camp_history);
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
        getSupportActionBar().setTitle(getString(R.string.camp_history));

        setupUiElements();
        RequestParams pp = new RequestParams();
        pp.put("user_id", MyApp.getApplication().readUser().getData().getId());
        postCall(getContext(), BASE_URL + "my-team", pp, "Please wait...", 1);
    }

    public int month = 6;
    public int year = 2018;

    private RequestParams p = new RequestParams();

    private void setupUiElements() {
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));

        select_month = findViewById(R.id.select_month);
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        int year = c.get(Calendar.YEAR);
        p.put("month", month);
        p.put("year", year);
        select_month.setText(getMonth(month-1) + ", " + year);
        setTouchNClick(R.id.select_month);

        this.month = month;
        this.year = year;
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
                    month = selectedMonth + 1;
                    year = selectedYear;
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
        return CampHistoryActivity.this;
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1 && o.optBoolean("status")) {
            MyTeam team = new Gson().fromJson(o.toString(), MyTeam.class);
            MyTeamAdapter adapter = new MyTeamAdapter(getContext(), team.getData().get(0).getChild());
            rv_list.setAdapter(adapter);
        } else if (callNumber == 2 && o.optBoolean("status")) {
            Camp c = new Gson().fromJson(o.toString(), Camp.class);
            SingleInstance.getInstance().setHistoryCamp(c);
            if (c.getData().size() == 0) {
                MyApp.popMessage("Message", "No camp created yet for the selected employee and month.", getContext());
                return;
            }
            startActivity(new Intent(getContext(), CampHistoryRMDetailsActivity.class).putExtra("month",
                    select_month.getText().toString()).putExtra("name", empName));
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

    private String empName;

    public void callHistoryApi(MyTeam.Data data) {
        empName = data.getName();
        RequestParams p = new RequestParams();
        p.put("emp_id", data.getId());
        p.put("month", month);
        p.put("year", year);
        postCall(getContext(), BASE_URL + "camp-history-rm", p, "Loading...", 2);
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
}
