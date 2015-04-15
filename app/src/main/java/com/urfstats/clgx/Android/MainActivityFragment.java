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
import android.widget.Button;

import com.urfstats.clgx.Core.DataController;
import com.urfstats.clgx.LoLData.StaticData;
import com.urfstats.clgx.LoLData.URFGames;
import com.urfstats.clgx.R;

import java.util.Date;

public class MainActivityFragment extends Fragment {

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

        Button dateButton = (Button) getActivity().findViewById(R.id.datePickerButton);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerFragment timePicker = new TimePickerFragment();
                DatePickerFragment datePicker = new DatePickerFragment();
                timePicker.setDatePicker(datePicker);
                timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");

            }
        });

    }

}

