package com.urfstats.clgx.Android;

/*
*   Class Made by pablo0910 - 2015
*   GitHub: https://github.com/pablo0910
*   Mail: pablo0910@hotmail.com
*   Class Done for Riot Api Challenge 2015
*
*   This class is the Fragment which shows a RecicleView containing all possible stats.
*
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.urfstats.clgx.Core.DataController;
import com.urfstats.clgx.R;

import java.io.File;
import java.util.Date;

public class StatsListActivityFragment extends Fragment {

    private static final String ARG_PARAM1 = "date1";
    private static final String ARG_PARAM2 = "date2";

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
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

        date1 = null;
        date2 = null;

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

        ProgressBar bar = (ProgressBar) getActivity().findViewById(R.id.retrievingDataProgressBar);
        TextView textView = (TextView) getActivity().findViewById(R.id.retrievingDataTextView);

        if (date1!=null && date2!=null) {

            Intent alarmIntent = new Intent(getActivity(), GamesGetter.class);
            Bundle dateContainer = new Bundle();
            if (date1.compareTo(date2) <= 0) {

                dateContainer.putSerializable("date1", date1);
                dateContainer.putSerializable("date2", date2);

            } else {

                dateContainer.putSerializable("date1", date2);
                dateContainer.putSerializable("date2", date1);

            }
            alarmIntent.putExtras(dateContainer);
            getActivity().startService(alarmIntent);

        }

        final DataController data = new DataController(getActivity().getFilesDir().toString());
        final String[] statsTitle =  { "Craziest Match", "Less Crazy Match", "Most Deaths", "Less Deaths", "Richest Match", "Poorest Match",
                "Too Strong AutoAttack", "Best Farming Match", "Worst Farming Match", "Most PENTAKILLS", "Too much DMG", "That CC", "Most Vision",
                "No Vision", "Most Baron Kills", "Longest Match", "Shortest Match" }; //"OP Champ", "Worst Champ"
        final String[] statsDescription = { "Shows the match with the majority of the kills", "Shows the match with less kills", "Shows the match with most deaths",
                "Shows the match with less deaths", "Shows the match with most gold earned", "Shows the match with less money earned", "Shows the match with the largest critical strike",
                "Shows the match with most minions killed", "Shows the match with less minions killed", "Shows the match with most PENTAKILLS",
                "Shows the match with most damage dealt to champs", "Shows the match where champs were most time under CrowdControl",
                "Shows the match with most wards placed", "Shows the match with less wards placed", "Shows the match with most Baron Nashor kills",
                "Shows the longest match", "Shows the shortest match", "Shows the most banned Champ" }; //"Shows the less banned Champ"

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.statsList);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getActivity(), null));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(statsTitle, statsDescription);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {

                Intent gameShower = new Intent(getActivity(), MatchPresenterActivity.class);
                Bundle game = new Bundle();
                game.putString("statTitle", statsTitle[position]);
                game.putSerializable("match", data.getStats().getStatistics().get(position).game);
                game.putInt("position", position);
                gameShower.putExtras(game);
                getActivity().startActivity(gameShower);

            }
        });

        if (!StatsListActivity.DATAREADY && !new File(getActivity().getFilesDir(),GamesGetter.STATSFILENAME).exists()) {

            mRecyclerView.setVisibility(RecyclerView.INVISIBLE);
            bar.setVisibility(ProgressBar.VISIBLE);
            textView.setVisibility(TextView.VISIBLE);

        } else {

            mRecyclerView.setVisibility(RecyclerView.VISIBLE);
            bar.setVisibility(ProgressBar.INVISIBLE);
            textView.setVisibility(TextView.INVISIBLE);

        }

    }

    /*@Override
    public void onPause() {
        super.onPause();

        Intent newActivity = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(newActivity);

    }*/

}
