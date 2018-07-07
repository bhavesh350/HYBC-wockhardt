package com.kybcwockhardt.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kybcwockhardt.CampHistoryDetailsActivity;
import com.kybcwockhardt.R;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.MyTeam;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class CampHistoryZSMAdapter extends RecyclerView.Adapter<CampHistoryZSMAdapter.MyViewHolder> {

    List<MyTeam.Data> data;
    private LayoutInflater inflater;
    private Context context;
    private String month;

    public CampHistoryZSMAdapter(Context context, List<MyTeam.Data> data, String date) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        month = date;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_camp_history_manager, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyTeam.Data d = data.get(position);
        holder.txt_name.setText(d.getName());

        if (d.getDesignation().equals("ZSM")) {
            holder.txt_patients.setText("Total Patients\n" + d.getZsm_patient_count_orignal());
            holder.txt_camps.setText("Total Camps\n" + d.getZsm_camp_count());
            if (d.getZsm_camp_count() == 0) {
                holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_red));
            } else {
                holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_green));
            }
        } else {
            holder.txt_patients.setText("Total Patients\n" + d.getRm_patient_count_orignal());
            holder.txt_camps.setText("Total Camps\n" + d.getRm_camp_count());
            if (d.getRm_camp_count() == 0) {
                holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_red));
            } else {
                holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_green));
            }
        }


        holder.txt_designation.setText("" + d.getDesignation());
        holder.txt_emp_id.setText("" + d.getEmp_no());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_name, txt_patients, txt_camps, txt_designation, txt_emp_id;
        CardView card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
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

            if (data.get(getLayoutPosition()).getRm_camp_count() == 0 && data.get(getLayoutPosition()).getDesignation().equals("RM")) {
                return;
            }

            if (data.get(getLayoutPosition()).getZsm_camp_count() == 0 && data.get(getLayoutPosition()).getDesignation().equals("ZSM")) {
                return;
            }
            SingleInstance.getInstance().setZsmHistoryData(data.get(getLayoutPosition()));

            context.startActivity(new Intent(context, CampHistoryDetailsActivity.class).putExtra("month",
                    month));

        }
    }
}
