package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.kybcwockhardt.application.AppConstants;
import com.kybcwockhardt.application.MyApp;

public class RmMainActivity extends CustomActivity {

    private TextView txt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rm_main);
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
    }

    private void setupUiElements() {
        txt_name = findViewById(R.id.txt_name);
        txt_name.setText("Logged in as : " + MyApp.getApplication().readUser().getData().getName());
        setClick(R.id.btn_notification);
        setClick(R.id.btn_my_team);
        setClick(R.id.btn_camp_approval);
        setClick(R.id.btn_leaderboard);
        setClick(R.id.btn_camp_history);
        setClick(R.id.txt_logout);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_my_team) {
            startActivity(new Intent(getContext(), MyTeamActivity.class).putExtra(AppConstants.EXTRA, false));
        } else if (v.getId() == R.id.btn_camp_approval) {
            startActivity(new Intent(getContext(), MyTeamActivity.class).putExtra(AppConstants.EXTRA, true));
        } else if (v.getId() == R.id.btn_camp_history) {
            startActivity(new Intent(getContext(), CampHistoryActivity.class).putExtra(AppConstants.EXTRA, true));
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
        return RmMainActivity.this;
    }
}
