package com.urfstats.clgx.Android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.urfstats.clgx.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.StatsViewHolder> {
    private String[] title, description;

    public class StatsViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected TextView vDescription;

        public StatsViewHolder(View v) {
            super(v);

            vTitle =  (TextView) v.findViewById(R.id.statTitle);
            vDescription = (TextView)  v.findViewById(R.id.statDescription);

        }

    }

    public RecyclerViewAdapter(String[] title, String[] description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public RecyclerViewAdapter.StatsViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stats_holder, parent, false);

        RecyclerViewAdapter.StatsViewHolder vh = new RecyclerViewAdapter.StatsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.StatsViewHolder holder, int position) {

        holder.vTitle.setText(title[position]);
        holder.vDescription.setText(description[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return title.length;
    }
}
