package com.kybcwockhardt;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.kybcwockhardt.adapters.ExecuteCampsAdapter;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.models.Camp;
import com.kybcwockhardt.models.Doctor;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class CampExecutionActivity extends CustomActivity implements CustomActivity.ResponseCallback{

    private Toolbar toolbar;
    private RecyclerView rv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_camp_execution);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.execute_camp));
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
        setupUiElements();

        RequestParams p = new RequestParams();
        p.put("user_id", MyApp.getApplication().readUser().getData().getId());
        postCall(getContext(), BASE_URL + "my-camp", p, "Fetching camp data...", 1);
    }

    private void setupUiElements() {
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private Context getContext() {
        return CampExecutionActivity.this;
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1 && o.optBoolean("status")) {
            Camp c = new Gson().fromJson(o.toString(), Camp.class);
            if (c.getData().size() == 0) {
                MyApp.popFinishableMessage("Message", "No camp created yet", CampExecutionActivity.this);
            } else {
                ExecuteCampsAdapter adapter = new ExecuteCampsAdapter(getContext(), c.getData());
                rv_list.setAdapter(adapter);
            }
        } else {
            MyApp.popFinishableMessage("Error", o.optJSONArray("data").optString(0), CampExecutionActivity.this);
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
