package com.urfstats.clgx.GUI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.urfstats.clgx.LoLData.Game;
import com.urfstats.clgx.LoLData.StaticData;
import com.urfstats.clgx.LoLData.URFGames;
import com.urfstats.clgx.R;

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
    public void onStart() {
        super.onStart();

        MainActivityFragment.GetStaticData staticData = new GetStaticData();
        staticData.execute();

        URFGames games = new URFGames();

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

