package com.urfstats.clgx.Android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urfstats.clgx.Core.DataController;
import com.urfstats.clgx.LoLData.StaticData;
import com.urfstats.clgx.LoLData.URFGames;
import com.urfstats.clgx.R;

import java.util.Date;

public class MainActivityFragment extends Fragment implements DatePickerFragment.DateGetter {

    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private Date beginDate;
    private Date endDate;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mainactivity_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        TimePickerFragment timePicker = new TimePickerFragment();
        DatePickerFragment datePicker = new DatePickerFragment();
        timePicker.setDatePicker(datePicker, this);
        timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");

        /*MainActivityFragment.GetStaticData staticData = new GetStaticData();
        staticData.execute();

        //URFGames games = new URFGames();

        Intent alarmIntent = new Intent(getActivity(), ServiceThrower.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);
        stopAlarm();
        startAlarm();

        DataController data = new DataController(getActivity().getFilesDir().toString());*/

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

    @Override
    public void setDate(Date date) {

        this.beginDate = date;
        System.out.println(date);

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

