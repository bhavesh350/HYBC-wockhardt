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
import com.kybcwockhardt.adapters.LeaderBoardAdapter;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.models.LeaderBoard;
import com.kybcwockhardt.models.User;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class LeaderboardActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private RecyclerView rv_list;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_listing);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.leaderboard));
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
        postCall(getContext(), BASE_URL + "leaderboards", new RequestParams(), "Please wait...", 1);
    }

    private void setupUiElements() {
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private Context getContext() {
        return LeaderboardActivity.this;
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (o.optBoolean("status")) {
            LeaderBoard l = new Gson().fromJson(o.toString(), LeaderBoard.class);
            LeaderBoardAdapter adapter = new LeaderBoardAdapter(getContext(), l.getData());
            rv_list.setAdapter(adapter);
            int myRank = 0;
            User me = MyApp.getApplication().readUser();
            for (int i = 0; i < l.getData().size(); i++) {
                if (me.getData().getId() == l.getData().get(i).getId()) {
                    myRank = i + 1;
                }
            }


            if (myRank == 0) {
//                MyApp.popMessage("Your Rank", "You do not obtain rank in top 100", getContext());
            } else {
                MyApp.popMessage("Your Rank", "You obtained "+myRank+" rank in top 100", getContext());
            }

        } else {
            MyApp.popFinishableMessage("Error", "No data available", LeaderboardActivity.this);
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
        MyApp.popFinishableMessage("Error", error, LeaderboardActivity.this);
    }
}
