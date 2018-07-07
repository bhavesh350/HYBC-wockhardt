package com.kybcwockhardt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.kybcwockhardt.adapters.MyTeamAdapter;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.models.Doctor;
import com.kybcwockhardt.models.MyTeam;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.kybcwockhardt.application.AppConstants.BASE_URL;

public class ZsmTeamActivity extends CustomActivity implements CustomActivity.ResponseCallback{

    private RecyclerView rv_list;
    private Toolbar toolbar;
    private boolean isNextLevel;
    private int myId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        isNextLevel = getIntent().getBooleanExtra("isNext", false);
        myId = getIntent().getIntExtra("myId", 0);
        setContentView(R.layout.activity_listing);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.my_team));
        setupUiElements();
        RequestParams p = new RequestParams();
        if (isNextLevel) {

        } else{
            p.put("user_id", MyApp.getApplication().readUser().getData().getId());
            postCall(getContext(), BASE_URL + "my-team", p, "Loading team data...", 1);
        }


    }

    private void setupUiElements() {
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private List<Doctor.Data> doctorsList = new ArrayList<>();

    void filter(String text) {
        try {
            List<Doctor.Data> temp = new ArrayList();
            for (Doctor.Data d : doctorsList) {
                if (d.getName().toLowerCase().contains(text)) {
                    temp.add(d);
                }
            }
//            adapter.updateList(temp);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Context getContext() {
        return ZsmTeamActivity.this;
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1 && o.optBoolean("status")) {
            MyTeam team = new Gson().fromJson(o.toString(), MyTeam.class);
            MyTeamAdapter adapter = new MyTeamAdapter(getContext(), team.getData());
            rv_list.setAdapter(adapter);
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
    public void goNextLevel(MyTeam.Data team) {
        if (isNextLevel) {
            startActivity(new Intent(getContext(), ZsmTeamActivity.class).putExtra("myId", team.getId())
                    .putExtra("isNext", true));
        }
    }
}
