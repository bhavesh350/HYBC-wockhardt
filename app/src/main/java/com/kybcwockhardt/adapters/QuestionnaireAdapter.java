package com.kybcwockhardt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.kybcwockhardt.R;
import com.kybcwockhardt.models.Doctor;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.MyViewHolder> {

    List<Doctor> data;
    private LayoutInflater inflater;
    private Context context;

    public QuestionnaireAdapter(Context context, List<Doctor> data) {
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
        View view = inflater.inflate(R.layout.item_questions, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        String current = data.get(position).getName();
        holder.txt_question.setText((position + 1) + ". Question details");
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_question;
        RadioGroup radioGroup;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_question = itemView.findViewById(R.id.txt_question);
            radioGroup = itemView.findViewById(R.id.radio_group);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
