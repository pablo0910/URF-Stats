package com.urfstats.clgx.Android;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int hour;
    private int minute;
    private Date date1, date2 = null;

    public void setTimeOfDay(int hourOfDay, int minute) {

        this.hour = hourOfDay;
        this.minute = minute;

    }

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

    public void onDateSet(DatePicker view, int year, int month, int day) {

        Calendar temp = Calendar.getInstance();
        temp.set(year, month, day, hour,minute);

        Date date = new Date(temp.getTimeInMillis());

        /*
        *
        * Algorithm which creates a bucle in order to ask for 2 dates
        *
         */

        if (this.date1==null) {

            DatePickerFragment datePicker = new DatePickerFragment();
            datePicker.setDate(date);
            TimePickerFragment timePicker = new TimePickerFragment();
            timePicker.setDatePicker(datePicker);
            timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");

        } else {

            this.date2 = date;
            Intent newActivity = new Intent(getActivity(), StatsListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("date1", this.date1);
            bundle.putSerializable("date2", this.date2);
            newActivity.putExtras(bundle);
            System.out.println(date1+"||"+date2);
            getActivity().startActivity(newActivity);

        }

    }

    public void setDate(Date date) {

        this.date1 = date;

    }

}
