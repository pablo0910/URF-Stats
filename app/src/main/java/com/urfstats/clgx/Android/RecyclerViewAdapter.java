package com.urfstats.clgx.Android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.urfstats.clgx.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.StatsViewHolder> {

    private String[] title, description;
    OnItemClickListener mItemClickListener;

    public class StatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView vTitle;
        protected TextView vDescription;

        public StatsViewHolder(View v) {
            super(v);
            vTitle =  (TextView) v.findViewById(R.id.statTitle);
            vDescription = (TextView)  v.findViewById(R.id.statDescription);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }

        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
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
        v.setClickable(true);
        v.setFocusable(true);

        RecyclerViewAdapter.StatsViewHolder vh = new RecyclerViewAdapter.StatsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.StatsViewHolder holder, int position) {

        holder.vTitle.setText(title[position]);
        holder.vDescription.setText(description[position]);

    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
