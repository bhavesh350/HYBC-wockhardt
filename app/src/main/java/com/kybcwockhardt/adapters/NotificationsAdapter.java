package com.kybcwockhardt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kybcwockhardt.R;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.models.Notification;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {

    List<Notification.Data> data;
    private LayoutInflater inflater;
    private Context context;

    public NotificationsAdapter(Context context, List<Notification.Data> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_notification, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notification.Data d = data.get(position);
        holder.txt_body.setText(d.getBody());
        holder.txt_date.setText(MyApp.parseDateSortDate(d.getCreated_at()));
        holder.txt_title.setText(d.getTitle());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_title;
        TextView txt_date;
        TextView txt_body;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_body = itemView.findViewById(R.id.txt_body);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
