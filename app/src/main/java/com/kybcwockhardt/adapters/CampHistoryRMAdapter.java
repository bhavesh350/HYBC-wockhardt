package com.kybcwockhardt.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kybcwockhardt.CampHistoryDetailsActivity;
import com.kybcwockhardt.CampHistoryZSMActivity;
import com.kybcwockhardt.R;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Camp;
import com.kybcwockhardt.models.MyTeam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class CampHistoryRMAdapter extends RecyclerView.Adapter<CampHistoryRMAdapter.MyViewHolder> {

    List<MyTeam.Data> data;
    private LayoutInflater inflater;
    private Context context;

    public CampHistoryRMAdapter(Context context, List<MyTeam.Data> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_camp_history_rm, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyTeam.Data d = data.get(position);
        holder.txt_name.setText(d.getName());
        int ccc = 0;
        int ddd = 0;
        for (Camp.Data dd : d.getCamps()) {
            if (dd.getStatus() == 2) {
                ccc += dd.getPatients().size();
                ddd += dd.getPatient_count();
            }
        }

        holder.txt_designation.setText("" + d.getDesignation());
        holder.txt_emp_id.setText("" + d.getEmp_no());

        List<Camp.Data> campList = new ArrayList<>();

        for (Camp.Data dd : d.getCamps()) {
            if (dd.getStatus() == 2) {
                campList.add(dd);
            }
        }
        holder.txt_patients.setText("Total Patients\n" + ccc + "\n"
                + "Expected patients\n" + ddd);
        holder.txt_camps.setText("Total Camps\n" + campList.size());
        TeamCampsAdapter adapter = new TeamCampsAdapter(context, campList);
        holder.rv_camp.setAdapter(adapter);

        if (d.getCamps().size() == 0) {
            holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_red));
        } else {
            holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_green));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_name, txt_patients, txt_camps, txt_designation, txt_emp_id;
        RecyclerView rv_camp;
        CardView card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            rv_camp = itemView.findViewById(R.id.rv_camp);
            rv_camp.setLayoutManager(new LinearLayoutManager(context));
            rv_camp.setNestedScrollingEnabled(false);
            txt_name = itemView.findViewById(R.id.txt_name);
            card_view = itemView.findViewById(R.id.card_view);
            txt_patients = itemView.findViewById(R.id.txt_patients);
            txt_designation = itemView.findViewById(R.id.txt_designation);
            txt_camps = itemView.findViewById(R.id.txt_camps);
            txt_emp_id = itemView.findViewById(R.id.txt_emp_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SingleInstance.getInstance().setZsmHistoryData(data.get(getLayoutPosition()));
            try {
                context.startActivity(new Intent(context, CampHistoryDetailsActivity.class).putExtra("month",
                        ((CampHistoryZSMActivity) context).select_month.getText().toString()));
            } catch (Exception e) {
            }
        }
    }
}
