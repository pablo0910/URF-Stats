package com.urfstats.clgx.Android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.urfstats.clgx.LoLData.Game;
import com.urfstats.clgx.R;

public class MatchPresenterFragment extends Fragment {

    private static final String ARG_PARAM1 = "game";

    private Game match;

    public static MatchPresenterFragment newInstance(Game match) {
        MatchPresenterFragment fragment = new MatchPresenterFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, match);
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

        TextView tv = (TextView) getActivity().findViewById(R.id.matchContainer);
        if (match!=null) tv.setText(match.toString());
        else tv.setText("No matches were loaded.\nTry with another date.");

    }

}
