package com.kybcwockhardt.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kybcwockhardt.QuestionnaireActivity;
import com.kybcwockhardt.R;
import com.kybcwockhardt.application.SingleInstance;
import com.kybcwockhardt.models.Patient;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class CampHistoryDetailsUsersAdapter extends RecyclerView.Adapter<CampHistoryDetailsUsersAdapter.MyViewHolder> {

    List<Patient.Data> data;
    private LayoutInflater inflater;
    private Context context;

    public CampHistoryDetailsUsersAdapter(Context context, List<Patient.Data> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_camp_history_user_details, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Patient.Data d = data.get(position);
        holder.txt_name.setText(d.getName());
        try {
            holder.txt_report.setText(d.getQuestion().getScore());
        } catch (Exception e) {
        }

        if (holder.txt_report.getText().toString().isEmpty() || holder.txt_report.getText().toString().equals("null")) {
            holder.txt_report.setText("Not measured");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_name, txt_report, txt_generate_score;
        CardView card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_generate_score = itemView.findViewById(R.id.txt_generate_score);
            txt_report = itemView.findViewById(R.id.txt_report);
            card_view = itemView.findViewById(R.id.card_view);
            txt_generate_score.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == txt_generate_score) {
                SingleInstance.getInstance().setPatient(data.get(getLayoutPosition()));
                context.startActivity(new Intent(context, QuestionnaireActivity.class).putExtra("position", getLayoutPosition()));
//                ((CampHistoryDetailsUsersActivity)context).finish();
            }
        }
    }
}
