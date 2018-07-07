package com.kybcwockhardt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kybcwockhardt.R;
import com.kybcwockhardt.models.Doctor;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class MyDoctorsAdapter extends RecyclerView.Adapter<MyDoctorsAdapter.MyViewHolder> {

    List<Doctor> data;
    private LayoutInflater inflater;
    private Context context;

    public MyDoctorsAdapter(Context context, List<Doctor> data) {
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
        View view = inflater.inflate(R.layout.item_doctor, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        String current = data.get(position).getName();
        holder.txt_name.setText("Doctor " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
