package com.urfstats.clgx.Android;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.urfstats.clgx.LoLData.Game;
import com.urfstats.clgx.LoLData.Participant;
import com.urfstats.clgx.LoLData.StaticData;
import com.urfstats.clgx.R;

public class MatchPresenterFragment extends Fragment {

    private static final String ARG_PARAM1 = "game";
    private static final String ARG_PARAM2 = "position";
    private static final String ARG_PARAM3 = "statTitle";

    private Game match;
    private int position;
    private String statTitle;

    public static MatchPresenterFragment newInstance(Game match, int position, String statTitle) {
        MatchPresenterFragment fragment = new MatchPresenterFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, match);
        args.putInt(ARG_PARAM2, position);
        args.putString(ARG_PARAM3, statTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public MatchPresenterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            match = (Game) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
            statTitle = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.matchpresenter_fragment, container, false);
    }

    @Override
     public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        MatchPresenterFragment.GetStaticData staticData = new GetStaticData();
        staticData.execute();

    }

    @SuppressLint("ResourceAsColor")
    private void showGUI() {

        ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.participantsContainer);
        TextView stat = (TextView) getActivity().findViewById(R.id.statTitleMatchPresenter);
        stat.setText(statTitle+": "+match.getStat(position));

        for (int i=0; i < Game.NUMPARTICIPANTS; i++) {

            Participant participant = match.getParticipants()[i];

            View newParticipant = getActivity().getLayoutInflater().inflate(R.layout.participant_info, scrollView);
            TextView championPlayed, killDeathAssist, pentaKills, champLevel, goldEarned, largestCrit, totalMinionsKilled
                    , damageDealtToChamps, crowdControlDone, wardsPlaced;
            RelativeLayout relativeLayout = (RelativeLayout) newParticipant.findViewById(R.id.relativeLayoutParticipantInfo);

            championPlayed = (TextView) newParticipant.findViewById(R.id.championPlayed);
            killDeathAssist = (TextView) newParticipant.findViewById(R.id.killDeathAssist);
            pentaKills = (TextView) newParticipant.findViewById(R.id.pentaKills);
            champLevel = (TextView) newParticipant.findViewById(R.id.champLevel);
            goldEarned = (TextView) newParticipant.findViewById(R.id.goldEarned);
            largestCrit = (TextView) newParticipant.findViewById(R.id.largestCrit);
            totalMinionsKilled = (TextView) newParticipant.findViewById(R.id.totalMinionsKilled);
            damageDealtToChamps = (TextView) newParticipant.findViewById(R.id.damageDealtToChamps);
            crowdControlDone = (TextView) newParticipant.findViewById(R.id.crowdControlDone);
            wardsPlaced = (TextView) newParticipant.findViewById(R.id.wardsPlaced);

            championPlayed.setText("Played: "+StaticData.champs.get(participant.getChampionId()));
            killDeathAssist.setText("KDA: "+participant.getKills()+"/"+participant.getDeaths()+"/"+participant.getAssists());
            pentaKills.setText("PentaKills: "+participant.getPentaKills());
            champLevel.setText("Champ Level: "+participant.getChampLevel());
            goldEarned.setText("Gold Earned: "+participant.getGoldEarned()+"g");
            largestCrit.setText("Largest Crit: "+participant.getLargestCriticalStrike());
            totalMinionsKilled.setText("Total Minions: "+participant.getTotalMinionsKilled());
            damageDealtToChamps.setText("Damage To Champs: "+participant.getTotalDamageDealtToChampions());
            crowdControlDone.setText("CC Done: "+participant.getTotalTimeCrowdControlDealt());
            wardsPlaced.setText("Wards Placed: " + participant.getWardsPlaced());

            if (participant.getTeamId() == 100) relativeLayout.setBackgroundColor(R.color.blue_600);
            else relativeLayout.setBackgroundColor(R.color.red_600);

            scrollView.addView(newParticipant);

        }

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

            showGUI();

        }

    }

}
