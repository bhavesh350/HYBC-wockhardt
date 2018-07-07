package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.kybcwockhardt.application.AppConstants;
import com.kybcwockhardt.application.MyApp;

public class ZsmSmMainActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zsm_main);

        setupUiElements();
    }

    private void setupUiElements() {

        setClick(R.id.btn_notification);
        setClick(R.id.btn_my_team);
        setClick(R.id.btn_leaderboard);
        setClick(R.id.btn_camp_history);
        setClick(R.id.txt_logout);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_my_team) {
            startActivity(new Intent(getContext(), MyTeamActivity.class).putExtra(AppConstants.EXTRA, false));
        } else if (v.getId() == R.id.btn_camp_history) {
            if (MyApp.getApplication().readUser().getData().getDesignation().equals("NSM")) {
                startActivity(new Intent(getContext(), CampHistoryNSMActivity.class).putExtra(AppConstants.EXTRA, true));
                return;
            }
            startActivity(new Intent(getContext(), CampHistoryZSMActivity
                    .class).putExtra(AppConstants.EXTRA, true));
        } else if (v.getId() == R.id.btn_notification) {
            startActivity(new Intent(getContext(), NotificationsActivity.class));
        } else if (v.getId() == R.id.btn_leaderboard) {
            startActivity(new Intent(getContext(), LeaderboardActivity.class));
        } else if (v.getId() == R.id.txt_logout) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            finishAffinity();
        }

    }

    private Context getContext() {
        return ZsmSmMainActivity.this;
    }
}
