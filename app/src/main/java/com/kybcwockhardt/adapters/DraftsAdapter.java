package com.kybcwockhardt.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kybcwockhardt.CampHistoryDetailsActivity;
import com.kybcwockhardt.DraftsActivity;
import com.kybcwockhardt.R;
import com.kybcwockhardt.application.MyApp;
import com.kybcwockhardt.models.Doctor;
import com.kybcwockhardt.models.Patient;

import java.util.List;

/**
 * Created by Abhishek on 22-04-2017.
 */

public class DraftsAdapter extends RecyclerView.Adapter<DraftsAdapter.MyViewHolder> {

    List<Patient.Data> data;
    private LayoutInflater inflater;
    private Context context;


    public DraftsAdapter(Context context, List<Patient.Data> data) {
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
        View view = inflater.inflate(R.layout.item_drafts, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Patient.Data d = data.get(position);
        holder.txt_name.setText(d.getName());

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_name;
        TextView txt_prescribed, txt_not_prescribed;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_not_prescribed = itemView.findViewById(R.id.txt_not_prescribed);
            txt_prescribed = itemView.findViewById(R.id.txt_prescribed);
            txt_prescribed.setOnClickListener(this);
            txt_not_prescribed.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == txt_prescribed) {
                final String items[] = {"Brand 1", "Brand 2", "Brand 3", "Brand 4", "Brand 5",
                        "Brand 6", "Brand 7", "Brand 8", "Brand 9"};
                new AlertDialog.Builder(context).setTitle("Choose Brand")
                        .setSingleChoiceItems(items, -1, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                try {
                                    ((DraftsActivity) context).callApiPrescribed(data.get(getLayoutPosition()).getId(), items[selectedPosition]);
                                    dialog.dismiss();
                                    data.remove(getLayoutPosition());
                                    notifyDataSetChanged();
                                } catch (Exception e) {
                                }


                                // Do something useful withe the position of the selected radio button
                            }
                        })
                        .show();
            } else if (v == txt_not_prescribed) {
                final String items[] = {"Brand 1", "Brand 2", "Brand 3", "Brand 4", "Brand 5"};
                new AlertDialog.Builder(context).setTitle("Choose Brand")
                        .setSingleChoiceItems(items, -1, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                // Do something useful withe the position of the selected radio button
                                try {
                                    ((DraftsActivity) context).callApiNotPrescribed(data.get(getLayoutPosition()).getId(), items[selectedPosition]);
                                    dialog.dismiss();
                                    data.remove(getLayoutPosition());
                                    notifyDataSetChanged();
                                } catch (Exception e) {
                                }

                            }
                        })
                        .show();
            }


        }
    }
}
