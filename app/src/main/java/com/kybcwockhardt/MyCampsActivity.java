package com.kybcwockhardt;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.gson.Gson;
import com.kybcwockhardt.adapters.MyCampsAdapter;
import com.kybcwockhardt.application.AppConstants;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.models.Camp;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class MyCampsActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private RecyclerView rv_list;
    private Toolbar toolbar;
    public boolean amIRm = false;
    private MyCampsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        setResponseListener(this);
        amIRm = getIntent().getBooleanExtra(AppConstants.EXTRA, false);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        if (amIRm)
            getSupportActionBar().setTitle(getString(R.string.camp_approval));
        else
            getSupportActionBar().setTitle(getString(R.string.my_camps));
        setupUiElements();


        RequestParams p = new RequestParams();
        if (amIRm)
            p.put("user_id", getIntent().getIntExtra("myId", 0));
        else
            p.put("user_id", MyApp.getApplication().readUser().getData().getId());
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        int year = c.get(Calendar.YEAR);
        p.put("month", month);
        p.put("year", year);
        postCall(getContext(), BASE_URL + "camp-history-for-tm", p, "Loading...", 1);
    }

    private void setupUiElements() {
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    private Context getContext() {
        return MyCampsActivity.this;
    }

    private int positionTo;

    public void openApprovalCampDialog(final int id, final int position) {
        positionTo = position;
        if (!MyApp.getApplication().readUser().getData().getDesignation().equals("RM")) {
            MyApp.popMessage("Alert", "You do not have permission to accept or reject camp.", getContext());
            return;
        }
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.approval_dialog);

        Button btn_approve = dialog.findViewById(R.id.btn_approve);
        Button btn_reject = dialog.findViewById(R.id.btn_reject);

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                MyApp.showMassage(getContext(), "Color will be change based on the real data.");
                RequestParams p = new RequestParams();
                p.put("camp_id", id);
                p.put("status", 2);
                postCall(getContext(), BASE_URL + "camp-response", p, "Approving...", 2);
            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                RequestParams p = new RequestParams();
                p.put("camp_id", id);
                p.put("status", 0);
                postCall(getContext(), BASE_URL + "camp-response", p, "Rejecting...", 2);
            }
        });

        dialog.show();
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1 && o.optBoolean("status")) {
            Camp c = new Gson().fromJson(o.toString(), Camp.class);
            if (c.getData().size() == 0) {
                MyApp.popFinishableMessage("Message", "No camp created yet", MyCampsActivity.this);
            } else {
                List<Camp.Data> campData = c.getData();
                for (int i = 0; i < c.getData().size(); i++) {
                    if (c.getData().get(i).getStatus() == 2) {
                        campData.remove(i);
                    }
                }
                adapter = new MyCampsAdapter(getContext(), campData);
                rv_list.setAdapter(adapter);
            }
        } else if (callNumber == 2 && o.optBoolean("status")) {
            adapter.data.get(positionTo).setStatus(o.optJSONObject("data").optInt("status"));
            adapter.notifyDataSetChanged();
        } else {
            MyApp.popFinishableMessage("Error", o.optJSONArray("data").optString(0), MyCampsActivity.this);
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
