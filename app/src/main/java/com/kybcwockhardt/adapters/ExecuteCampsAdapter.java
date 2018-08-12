package com.kybcwockhardt.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kybcwockhardt.CampExecutionClickActivity;
import com.kybcwockhardt.R;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Camp;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class ExecuteCampsAdapter extends RecyclerView.Adapter<ExecuteCampsAdapter.MyViewHolder> {

    private List<Camp.Data> data;
    private LayoutInflater inflater;
    private Context context;

    public ExecuteCampsAdapter(Context context, List<Camp.Data> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_my_camps, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Camp.Data d = data.get(position);
        holder.txt_name.setText(d.getDoctor().getName() + " " + "(" + d.getDoctor().getMsl_code() + ")");
        holder.txt_count.setText(d.getPatient_count() + " Patients");
        holder.txt_date.setText(MyApp.parseDateFullMonth(d.getCamp_date().split(" ")[0]));
        if (d.getStatus() == 2 && MyApp.getTodayDate(System.currentTimeMillis()).equals(holder.txt_date.getText().toString())) {
            holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_green));
        } else if (d.getStatus() == 1) {
            holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_yellow));
        }else if (d.getStatus() == 0) {
            holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.card_red));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_name;
        TextView txt_count;
        TextView txt_date;
        CardView card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_date = itemView.findViewById(R.id.txt_date);
            card_view = itemView.findViewById(R.id.card_view);
            txt_count = itemView.findViewById(R.id.txt_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (data.get(getLayoutPosition()).getStatus() == 2 &&
                    MyApp.getTodayDate(System.currentTimeMillis()).equals(MyApp.parseDateFullMonth(
                            data.get(getLayoutPosition()).getCamp_date().split(" ")[0]))) {
                SingleInstance.getInstance().setSelectedCamp(data.get(getLayoutPosition()));
                context.startActivity(new Intent(context, CampExecutionClickActivity.class));
            } else {
                if (data.get(getLayoutPosition()).getStatus() == 1) {
                    MyApp.popMessage("Alert", "Your camp is not approved yet.", context);
                } else if (data.get(getLayoutPosition()).getStatus() == 0) {
                    MyApp.popMessage("Alert", "Your camp has been rejected by your RM.", context);
                } else {
                    MyApp.showMassage(context, "It's a past camp");
                }
            }

        }
    }
}
