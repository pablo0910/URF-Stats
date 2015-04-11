package com.urfstats.clgx.LoLData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Game implements Serializable {

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

    public long totalKills() {

        long numKills = 0;
        for (int i=0; i < this.NUMPARTICIPANTS; i++) {

            numKills += participants[i].getKills();

        }

        return numKills;

    }

    public long totalDeaths() {

        long numDeaths = 0;
        for (int i=0; i < this.NUMPARTICIPANTS; i++) {

            numDeaths += participants[i].getDeaths();

        }

        return numDeaths;

    }

    public long goldEarned() {

        long goldEarned = 0;
        for (int i=0; i < this.NUMPARTICIPANTS; i++) {

            goldEarned += participants[i].getGoldEarned();

        }

        return goldEarned;

    }

    public long largestCriticalStrike() {

        long largestCrit = participants[0].getLargestCriticalStrike();
        for (int i=1; i < this.NUMPARTICIPANTS; i++) {

            if (((Long) largestCrit).compareTo((Long) participants[i].getLargestCriticalStrike()) < 0) {

                largestCrit = participants[i].getLargestCriticalStrike();

            }

        }

        return largestCrit;

    }

    public long minionsKilled() {

        long minionsKilled = 0;
        for (int i=0; i < this.NUMPARTICIPANTS; i++) {

            minionsKilled += participants[i].getTotalMinionsKilled();

        }

        return minionsKilled;

    }

    public long totalPentaKills() {

        long pentaKills = 0;
        for (int i=0; i < this.NUMPARTICIPANTS; i++) {

            pentaKills += participants[i].getPentaKills();

        }

        return pentaKills;

    }

    public long totalDamageToChamps() {

        long totalDmg = 0;
        for (int i=0; i < this.NUMPARTICIPANTS; i++) {

            totalDmg += participants[i].getTotalDamageDealtToChampions();

        }

        return totalDmg;

    }

    public long totalCrowdControl() {

        long totalCC = 0;
        for (int i=0; i < this.NUMPARTICIPANTS; i++) {

            totalCC += participants[i].getTotalTimeCrowdControlDealt();

        }

        return totalCC;

    }

    public long totalWardsPlaced() {

        long totalWards = 0;
        for (int i=0; i < this.NUMPARTICIPANTS; i++) {

            totalWards += participants[i].getWardsPlaced();

        }

        return totalWards;

    }

    public long totalBaronKills() {

        long baronKills = 0;
        for (int i=0; i < this.NUMTEAMS; i++) {

            baronKills += teams[i].getBaronKills();

        }

        return baronKills;

    }

    public ArrayList<Integer> getGameBans() {

        ArrayList<Integer> bans = new ArrayList<>();

        for (int i=0; i < this.NUMTEAMS; i++) {

            bans.addAll(teams[i].getBans());

        }

        return bans;

    }

    public long getMatchDuration() { return this.matchDuration; }

    @Override
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
    }
}
