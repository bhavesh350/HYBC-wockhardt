package com.kybcwockhardt.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kybcwockhardt.R;
import com.kybcwockhardt.models.Camp;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class CampHistoryRMDetailsAdapter extends RecyclerView.Adapter<CampHistoryRMDetailsAdapter.MyViewHolder> {

    List<Camp.Data> data;
    private LayoutInflater inflater;
    private Context context;

    public CampHistoryRMDetailsAdapter(Context context, List<Camp.Data> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_camp_history_manager, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Camp.Data d = data.get(position);
        holder.txt_name.setText(d.getDoctor().getName());


//        holder.txt_designation.setText("" + d.getDesignation());
        holder.txt_emp_id.setText("" + d.getMsl_code());


        holder.txt_patients.setText("Total Patients\n" + d.getPatients().size());
//        holder.txt_camps.setText("Total Camps\n" + campList.size());
//        TeamCampsAdapter adapter = new TeamCampsAdapter(context, campList);
//        holder.rv_camp.setAdapter(adapter);

//        if (d.getCamps().size() == 0) {
//            holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_red));
//        } else {
            holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_green));
//        }
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
            txt_designation.setVisibility(View.GONE);
            txt_camps = itemView.findViewById(R.id.txt_camps);
            txt_emp_id = itemView.findViewById(R.id.txt_emp_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            SingleInstance.getInstance().setZsmHistoryData(data.get(getLayoutPosition()));
//            context.startActivity(new Intent(context, CampHistoryDetailsActivity.class).putExtra("month",
//                    ((CampHistoryZSMActivity) context).select_month.getText().toString()));
        }
    }
}
