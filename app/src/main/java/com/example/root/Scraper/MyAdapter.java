package com.example.root.Scraper;

/**
 * Created by root on 23/4/17.
 */


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by root on 10/4/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.textViewHead.setText(listItem.getHead());
        holder.textViewAccess.setText("Accession no. " + listItem.getAccess());
        Calendar c = Calendar.getInstance();
        int curmonth = c.get(Calendar.MONTH) + 1;
        int curday = c.get(Calendar.DAY_OF_MONTH);
        int curyear = c.get(Calendar.YEAR);

        String[] mandd = listItem.getDue().split("/");
        int month = Integer.parseInt(mandd[0]);
        int day = Integer.parseInt(mandd[1]);
        int year = Integer.parseInt(mandd[2]);
        String due = month + "/" + day;
        if (year >= curyear) {
            if (month < curmonth) {
                holder.textViewDue.setTextColor(Color.parseColor("#E0183F"));
            }
            if ((month == curmonth) && (day > curday)) {
                holder.textViewDue.setTextColor(Color.parseColor("#5B85FA"));
            }
            if ((month == curmonth) && (day <= curday)) {
                holder.textViewDue.setTextColor(Color.parseColor("#E0183F"));
            }
            if (month > curmonth) {
                holder.textViewDue.setTextColor(Color.parseColor("#1A9B61"));
            }

        } else {
            holder.textViewDue.setTextColor(Color.parseColor("#E0183F"));
        }
        holder.textViewDue.setText("Due " + due);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHead;
        public TextView textViewAccess;
        public TextView textViewDue;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewAccess = (TextView) itemView.findViewById(R.id.textViewAccess);
            textViewDue = (TextView) itemView.findViewById(R.id.textViewDue);

        }
    }

}
