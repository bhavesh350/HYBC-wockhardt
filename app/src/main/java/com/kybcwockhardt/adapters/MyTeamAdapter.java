package com.kybcwockhardt.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kybcwockhardt.CampHistoryActivity;
import com.kybcwockhardt.MyCampsActivity;
import com.kybcwockhardt.MyTeamActivity;
import com.kybcwockhardt.R;
import com.kybcwockhardt.application.AppConstants;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.MyTeam;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class


MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.MyViewHolder> {

    List<MyTeam.Data> data;
    private LayoutInflater inflater;
    private Context context;

    public MyTeamAdapter(Context context, List<MyTeam.Data> data) {
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
            try {
                if (data.get(getLayoutPosition()).getDesignation().equals("TM")) {
                    if (MyApp.getApplication().readUser().getData().getDesignation().equals("RM")) {
                        if (!((MyTeamActivity) context).isGoNext) {
                            return;
                        }
                    }
                    context.startActivity(new Intent(context, MyCampsActivity.class).putExtra(AppConstants.EXTRA, true)
                            .putExtra("myId", data.get(getLayoutPosition()).getId()));
                } else {
                    if (data.get(getLayoutPosition()).getChild().size() > 0) {
                        SingleInstance.getInstance().setNextTeam(data.get(getLayoutPosition()).getChild());
                        ((MyTeamActivity) context).goNextLevel(data.get(getLayoutPosition()), true);
                    }
                }
            } catch (Exception e) {
                ((CampHistoryActivity)context).callHistoryApi(data.get(getLayoutPosition()));
            }

        }
    }
}
