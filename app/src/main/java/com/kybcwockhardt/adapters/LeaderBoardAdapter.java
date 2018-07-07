package com.kybcwockhardt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kybcwockhardt.R;
import com.kybcwockhardt.models.User;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {

    private List<User.Data> data;
    private LayoutInflater inflater;
    private Context context;

    public LeaderBoardAdapter(Context context, List<User.Data> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_leaderboard, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User.Data d = data.get(position);
        holder.txt_total_camp.setText("Total Camp\n" + data.get(position).getCampCount());

        holder.txt_exe_camp.setText("Camp Executed\n" + data.get(position).getCampExeCount());
        holder.txt_name.setText(d.getName() + "\n(" + d.getHq() + ")");
        holder.txt_rank.setText((position + 1) + "");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_total_camp;
        TextView txt_exe_camp;
        TextView txt_name;
        TextView txt_rank;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_total_camp = itemView.findViewById(R.id.txt_total_camp);
            txt_exe_camp = itemView.findViewById(R.id.txt_exe_camp);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_rank = itemView.findViewById(R.id.txt_rank);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
