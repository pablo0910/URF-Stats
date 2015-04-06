package com.urfstats.clgx.LoLData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Game {

    private final int NUMPARTICIPANTS = 10;
    private final int NUMTEAMS = 2;

    private long matchId;
    private String region;
    private long matchCreation;
    private long matchDuration;

    private Participant[] participants;
    private Team[] teams;

    public Game(JSONObject match) {

        try {

            this.region = match.getString("region");
            this.matchId = match.getLong("matchId");
            this.matchCreation = match.getLong("matchCreation");
            this.matchDuration = match.getLong("matchDuration");

            createParticipants(match.getJSONArray("participants"));
            createTeams(match.getJSONArray("teams"));

        } catch (JSONException e) {

            System.err.println("Error: "+e);

        }


    }

    private void createParticipants(JSONArray participants) {

        this.participants = new Participant[this.NUMPARTICIPANTS];

        for (int i=0; i < this.NUMPARTICIPANTS; i++) {

            try {

                this.participants[i] = new Participant((JSONObject) participants.get(i));

            } catch (JSONException e) {

                System.err.println("Error: " + e);

            }

        }

    }

    private void createTeams(JSONArray teams) {

        this.teams = new Team[this.NUMTEAMS];

        for (int i=0; i < this.NUMTEAMS; i++) {

            try {

                this.teams[i] = new Team((JSONObject) teams.get(i));

            } catch (JSONException e) {

                System.err.println("Error: " + e);

            }

        }

    }

    /*@Override
    public String toString() {
        return "Game{" +
                "NUMPARTICIPANTS=" + NUMPARTICIPANTS +
                ", NUMTEAMS=" + NUMTEAMS +
                ", matchId=" + matchId +
                ", region='" + region + '\'' +
                ", matchCreation=" + matchCreation +
                ", matchDuration=" + matchDuration +
                ", participants=" + Arrays.toString(participants) +
                ", teams=" + Arrays.toString(teams) +
                '}';
    }*/
}
