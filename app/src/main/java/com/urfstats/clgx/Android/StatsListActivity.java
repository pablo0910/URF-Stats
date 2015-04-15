package com.urfstats.clgx.Android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.urfstats.clgx.R;

import java.util.Date;

public class StatsListActivity extends ActionBarActivity {

    public StatsListActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statslist_activity);
        Bundle dateContainer = getIntent().getExtras();

        final Toolbar actionBar = (Toolbar) findViewById(R.id.actionBarUpButton);
        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragment = StatsListActivityFragment.newInstance((Date) dateContainer.getSerializable("date1"), (Date) dateContainer.getSerializable("date2"));
        getSupportFragmentManager().beginTransaction().replace(R.id.containerStatsListActivity, fragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
