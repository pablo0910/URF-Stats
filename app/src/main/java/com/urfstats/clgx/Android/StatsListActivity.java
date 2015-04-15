package com.urfstats.clgx.Android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.urfstats.clgx.R;

import java.util.Date;

public class StatsListActivity extends ActionBarActivity {

    public static boolean ACTIVITYALIVE = false;
    public static boolean DATAREADY = false;
    public StatsListActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statslist_activity);
        Bundle dateContainer = getIntent().getExtras();

        final Toolbar actionBar = (Toolbar) findViewById(R.id.actionBarUpButton);
        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (dateContainer!=null) fragment = StatsListActivityFragment.newInstance((Date) dateContainer.getSerializable("date1"), (Date) dateContainer.getSerializable("date2"));
        else fragment = new StatsListActivityFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerStatsListActivity, fragment).commit();

    }

    @Override
    public void onStart() {
        super.onStart();

        ACTIVITYALIVE = true;

    }

    @Override
    public void onStop() {
        super.onStart();

        ACTIVITYALIVE = false;

    }
}
