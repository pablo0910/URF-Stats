package com.urfstats.clgx.LoLData;

/*
*   Class Made by pablo0910 - 2015
*   GitHub: https://github.com/pablo0910
*   Mail: pablo0910@hotmail.com
*   Class Done for Riot Api Challenge 2015
*
*   This class stores all info related which each Team.
*
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {

    private int teamId;
    private int dragonKills;
    private int baronKills;
    private int inhibitorKills;
    private int towerKills;
    ArrayList<Integer> bans;
    private boolean firstBaron;
    private boolean firstBlood;
    private boolean firstDragon;
    private boolean firstInhibitor;
    private boolean firstTower;
    private boolean winner;

    public Team(JSONObject team) {

        try {

            this.teamId = team.getInt("teamId");
            this.dragonKills = team.getInt("dragonKills");
            this.baronKills = team.getInt("baronKills");
            this.inhibitorKills = team.getInt("inhibitorKills");
            this.towerKills = team.getInt("towerKills");

            this.firstBaron = team.getBoolean("firstBaron");
            this.firstBlood = team.getBoolean("firstBlood");
            this.firstDragon = team.getBoolean("firstDragon");
            this.firstInhibitor = team.getBoolean("firstInhibitor");
            this.firstTower = team.getBoolean("firstTower");
            this.winner = team.getBoolean("winner");

            bansBuilder(team.getJSONArray("bans"));

        } catch (JSONException e) {

            System.err.println("Error! "+e);

        }

    }

    private void bansBuilder(JSONArray bans) {

        this.bans = new ArrayList<>();
        JSONObject ban1, ban2, ban3;
        int ban1I, ban2I, ban3I;

        try {

            ban1 = bans.getJSONObject(0);
            ban2 = bans.getJSONObject(1);
            ban3 = bans.getJSONObject(2);

            ban1I = ban1.getInt("championId");
            ban2I = ban2.getInt("championId");
            ban3I = ban3.getInt("championId");

            this.bans.add((Integer) ban1I);
            this.bans.add((Integer) ban2I);
            this.bans.add((Integer) ban3I);

        } catch (JSONException e) {

            //System.err.println("Error: No se baneo "+e);

        }

    }

    public int getBaronKills() {
        return baronKills;
    }

    public ArrayList<Integer> getBans() {
        return bans;
    }

    public boolean isWinner() {
        return winner;
    }

    /*@Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", dragonKills=" + dragonKills +
                ", baronKills=" + baronKills +
                ", inhibitorKills=" + inhibitorKills +
                ", towerKills=" + towerKills +
                ", bans=" + bans +
                ", firstBaron=" + firstBaron +
                ", firstBlood=" + firstBlood +
                ", firstDragon=" + firstDragon +
                ", firstInhibitor=" + firstInhibitor +
                ", firstTower=" + firstTower +
                ", winner=" + winner +
                '}';
    }*/
}
