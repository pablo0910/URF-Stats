package com.urfstats.clgx.Android;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urfstats.clgx.Core.DataController;
import com.urfstats.clgx.LoLData.StaticData;
import com.urfstats.clgx.R;

import java.util.Date;

public class StatsListActivityFragment extends Fragment {

    private static final String ARG_PARAM1 = "date1";
    private static final String ARG_PARAM2 = "date2";
    private PendingIntent pendingIntent;
    private AlarmManager manager;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Date date1;
    private Date date2;

    public static StatsListActivityFragment newInstance(Date date1, Date date2) {
        StatsListActivityFragment fragment = new StatsListActivityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, date1);
        args.putSerializable(ARG_PARAM2, date2);
        fragment.setArguments(args);
        return fragment;
    }

    public StatsListActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date1 = (Date) getArguments().getSerializable(ARG_PARAM1);
            date2 = (Date) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.statslist_activity_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        /*StatsListActivityFragment.GetStaticData staticData = new GetStaticData();
        staticData.execute();

        //URFGames games = new URFGames();

        Intent alarmIntent = new Intent(getActivity(), ServiceThrower.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);
        stopAlarm();
        startAlarm();*/

        DataController data = new DataController(getActivity().getFilesDir().toString());
        String[] statsTitle =  { "Pablo" };
        String[] statsDescription = { "Jorge" };

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.statsList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(statsTitle, statsDescription);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void startAlarm() {
        manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        int interval = 3600*1000;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }

    public void stopAlarm() {
        manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        int interval = 3600*1000;
        manager.cancel(pendingIntent);
    }

    private class GetStaticData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... id) {

            StaticData data = new StaticData();
            data.getChamps();

            return null;

        }

        @Override
        protected void onPostExecute(String dummy) {

        }

    }

}
