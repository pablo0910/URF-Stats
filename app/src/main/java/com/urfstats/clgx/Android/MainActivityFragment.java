package com.urfstats.clgx.Android;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import com.urfstats.clgx.R;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class MainActivityFragment extends Fragment {

    private Date date1=null, date2=null;
    private int hour=0, minute=0, day=9, month=4, year=2015;
    TextView date1String, date2String;

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

        final Button dateButton1 = (Button) getActivity().findViewById(R.id.datePickerButton1);
        date1String = (TextView) getActivity().findViewById(R.id.date1String);
        final Button dateButton2 = (Button) getActivity().findViewById(R.id.datePickerButton2);
        date2String = (TextView) getActivity().findViewById(R.id.date2String);
        final Button showStats = (Button) getActivity().findViewById(R.id.showOldStatsButton);
        final Button newActivity = (Button) getActivity().findViewById(R.id.startStatsListActivity);
        final Button resetDate = (Button) getActivity().findViewById(R.id.resetDatesButton);

        if (!(new File(getActivity().getFilesDir(), GamesGetter.STATSFILENAME)).exists()) {

            showStats.setVisibility(Button.INVISIBLE);

        }

        dateButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MainActivityFragment.DatePickerFragment datePicker = new MainActivityFragment.DatePickerFragment();
                datePicker.show(getActivity().getSupportFragmentManager(), "datePicker1");
                MainActivityFragment.TimePickerFragment timePicker = new MainActivityFragment.TimePickerFragment();
                timePicker.show(getActivity().getSupportFragmentManager(), "timePicker1");

            }
        });

        dateButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MainActivityFragment.DatePickerFragment datePicker = new MainActivityFragment.DatePickerFragment();
                datePicker.show(getActivity().getSupportFragmentManager(), "datePicker2");
                MainActivityFragment.TimePickerFragment timePicker = new MainActivityFragment.TimePickerFragment();
                timePicker.show(getActivity().getSupportFragmentManager(), "timePicker2");

            }
        });

        newActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openStatsListActivity();

            }
        });

        resetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date1String.setText("");
                date2String.setText("");
                dateButton1.setVisibility(Button.VISIBLE);
                dateButton2.setVisibility(Button.VISIBLE);
                date1 = null;
                date2 = null;

            }
        });

        showStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newActivity = new Intent(getActivity(), StatsListActivity.class);
                getActivity().startActivity(newActivity);

            }
        });

    }

    private void openStatsListActivity() {

        if (date1!=null && date2!=null) {

            Intent newActivity = new Intent(getActivity(), StatsListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("date1", this.date1);
            bundle.putSerializable("date2", this.date2);
            newActivity.putExtras(bundle);
            getActivity().startActivity(newActivity);

        } else {

            date1String.setText("Error! Please, set Both Dates");

        }

    }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int yearL, int monthL, int dayL) {

            final Button dateButton1 = (Button) getActivity().findViewById(R.id.datePickerButton1);
            final Button dateButton2 = (Button) getActivity().findViewById(R.id.datePickerButton2);

            year = yearL;
            month = monthL;
            day = dayL;
            Calendar c = Calendar.getInstance();
            c.set(year, month, day, hour, minute);
            Date date = new Date(c.getTimeInMillis());
            if (date1 == null) { date1 = date; date1String.setText(date1.toString()); date2String.setText(""); }
            else { date2 = date; date2String.setText(date2.toString()); }

        }

    }

    @SuppressLint("ValidFragment")
    public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        public TimePickerFragment(){ }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {

            hour = hourOfDay;
            minute = minutes;

        }

    }

}

