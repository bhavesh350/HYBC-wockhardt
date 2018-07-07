package com.kybcwockhardt.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kybcwockhardt.CampHistoryNSMActivity;
import com.kybcwockhardt.CampHistoryZSMActivity;
import com.kybcwockhardt.R;
import com.kybcwockhardt.models.MyTeam;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class NSMTeamAdapter extends RecyclerView.Adapter<NSMTeamAdapter.MyViewHolder> {

    List<MyTeam.Data> data;
    private LayoutInflater inflater;
    private Context context;

    public NSMTeamAdapter(Context context, List<MyTeam.Data> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_my_team, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        String current = data.get(position).getName();
        holder.txt_name.setText(data.get(position).getName());
        holder.txt_emp_id.setText(data.get(position).getEmp_no() + "");
        holder.txt_head_quarter.setText(data.get(position).getHq());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_name;
        TextView txt_head_quarter;
        TextView txt_emp_id;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_emp_id = itemView.findViewById(R.id.txt_emp_id);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_head_quarter = itemView.findViewById(R.id.txt_head_quarter);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, CampHistoryZSMActivity.class);
            i.putExtra("isSM", true);
            i.putExtra("date", ((CampHistoryNSMActivity) context).select_month.getText().toString());
            i.putExtra("userId", data.get(getLayoutPosition()).getId());
            i.putExtra("year", ((CampHistoryNSMActivity) context).year);
            i.putExtra("month", ((CampHistoryNSMActivity) context).month);
            i.putExtra("designation",data.get(getLayoutPosition()).getDesignation());
            context.startActivity(i);
        }
    }
}
