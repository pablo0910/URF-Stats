package com.urfstats.clgx.LoLData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

public class Participant implements Serializable {

    private final int NUMITEMS = 7;

    private int participantId;
    private int championId;
    private String highestAchievedSeasonTier;
    private int teamId;
    private String role;
    private String lane;

    //Stats

    private long kills;
    private long deaths;
    private long assists;
    private long champLevel;
    private long doubleKills;
    private long goldEarned;
    private long goldSpent;
    private long inhibitorKills;
    private long[] items;
    private long largestCriticalStrike;
    private long largestKillingSpree;
    private long largestMultiKill;
    private long minionsKilled;
    private long neutralMinionsKilled;
    private long pentaKills;
    private long totalDamageDealt;
    private long totalDamageDealtToChampions;
    private long totalTimeCrowdControlDealt;
    private long wardsKilled;
    private long wardsPlaced;

    private boolean firstBloodParticipation;
    private boolean firstInhibitorParticipation;
    private boolean firstTowerParticipation;

    private ParticipantTimelineData creepsPerMinDeltas;
    private ParticipantTimelineData csDiffPerMinDeltas;
    private ParticipantTimelineData goldPerMinDeltas;

    public Participant(JSONObject participant) {

        items = new long[this.NUMITEMS];

        try {

            this.participantId = participant.getInt("participantId");
            this.championId = participant.getInt("championId");
            this.highestAchievedSeasonTier = participant.getString("highestAchievedSeasonTier");
            this.teamId = participant.getInt("teamId");

            JSONObject stats = participant.getJSONObject("stats");
            this.kills = stats.getLong("kills");
            this.deaths = stats.getLong("deaths");
            this.assists = stats.getLong("assists");
            this.champLevel = stats.getLong("champLevel");
            this.doubleKills = stats.getLong("doubleKills");
            this.goldEarned = stats.getLong("goldEarned");
            this.goldSpent = stats.getLong("goldSpent");
            this.inhibitorKills = stats.getLong("inhibitorKills");
            this.items[0] = stats.getLong("item0");
            this.items[1] = stats.getLong("item1");
            this.items[2] = stats.getLong("item2");
            this.items[3] = stats.getLong("item3");
            this.items[4] = stats.getLong("item4");
            this.items[5] = stats.getLong("item5");
            this.items[6] = stats.getLong("item6");
            this.largestCriticalStrike = stats.getLong("largestCriticalStrike");
            this.largestKillingSpree = stats.getLong("largestKillingSpree");
            this.largestMultiKill = stats.getLong("largestMultiKill");
            this.minionsKilled = stats.getLong("minionsKilled");
            this.neutralMinionsKilled = stats.getLong("neutralMinionsKilled");
            this.pentaKills = stats.getLong("pentaKills");
            this.totalDamageDealt = stats.getLong("totalDamageDealt");
            this.totalDamageDealtToChampions = stats.getLong("totalDamageDealtToChampions");
            this.totalTimeCrowdControlDealt = stats.getLong("totalTimeCrowdControlDealt");
            this.wardsKilled = stats.getLong("wardsKilled");
            this.wardsPlaced = stats.getLong("wardsPlaced");
            this.firstBloodParticipation = iParticipate(stats.getBoolean("firstBloodAssist"), stats.getBoolean("firstBloodKill"));
            this.firstInhibitorParticipation = iParticipate(stats.getBoolean("firstInhibitorAssist"), stats.getBoolean("firstInhibitorKill"));
            this.firstTowerParticipation = iParticipate(stats.getBoolean("firstTowerAssist"), stats.getBoolean("firstTowerKill"));

            JSONObject timeline = participant.getJSONObject("timeline");
            this.role = timeline.getString("role");
            this.lane = timeline.getString("lane");
            this.creepsPerMinDeltas = new ParticipantTimelineData(timeline.getJSONObject("creepsPerMinDeltas"));
            if (timeline.has("csDiffPerMinDeltas")) this.csDiffPerMinDeltas = new ParticipantTimelineData(timeline.getJSONObject("csDiffPerMinDeltas"));
            this.goldPerMinDeltas = new ParticipantTimelineData(timeline.getJSONObject("goldPerMinDeltas"));

        } catch (JSONException e) {

            System.err.println("Error "+e);

        }

    }

    private boolean iParticipate(boolean first, boolean second) {

        return first || second;

    }

    private class ParticipantTimelineData implements Serializable {

        public double tenToTwenty;
        public double thirtyToEnd;
        public double twentyToThirty;
        public double zeroToTen;

        public ParticipantTimelineData(JSONObject data) {

            this.tenToTwenty = -1;
            this.thirtyToEnd = -1;
            this.twentyToThirty = -1;
            this.zeroToTen = -1;

            try {

                if (data.has("zeroToTen")) this.zeroToTen = data.getDouble("zeroToTen");
                if (data.has("tenToTwenty")) this.tenToTwenty = data.getDouble("tenToTwenty");
                if (data.has("twentyToThirty")) this.twentyToThirty = data.getDouble("twentyToThirty");
                if (data.has("thirtyToEnd")) this.thirtyToEnd = data.getDouble("thirtyToEnd");

            } catch (JSONException e) {

                System.err.println("Error: "+e);

            }

        }

    }

    public long getKills() {
        return kills;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getAssists() {
        return assists;
    }

    public long getGoldEarned() {
        return goldEarned;
    }

    public long getTotalMinionsKilled() { return (minionsKilled+neutralMinionsKilled); }

    public long getLargestCriticalStrike() {
        return largestCriticalStrike;
    }

    public long getPentaKills() {
        return pentaKills;
    }

    public long getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    public long getTotalTimeCrowdControlDealt() {
        return totalTimeCrowdControlDealt;
    }

    public long getWardsPlaced() {
        return wardsPlaced;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "NUMITEMS=" + NUMITEMS +
                ", participantId=" + participantId +
                ", championId=" + championId +
                ", highestAchievedSeasonTier='" + highestAchievedSeasonTier + '\'' +
                ", teamId=" + teamId +
                ", role='" + role + '\'' +
                ", lane='" + lane + '\'' +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", assists=" + assists +
                ", champLevel=" + champLevel +
                ", doubleKills=" + doubleKills +
                ", goldEarned=" + goldEarned +
                ", goldSpent=" + goldSpent +
                ", inhibitorKills=" + inhibitorKills +
                ", items=" + Arrays.toString(items) +
                ", largestCriticalStrike=" + largestCriticalStrike +
                ", largestKillingSpree=" + largestKillingSpree +
                ", largestMultiKill=" + largestMultiKill +
                ", minionsKilled=" + minionsKilled +
                ", neutralMinionsKilled=" + neutralMinionsKilled +
                ", pentaKills=" + pentaKills +
                ", totalDamageDealt=" + totalDamageDealt +
                ", totalDamageDealtToChampions=" + totalDamageDealtToChampions +
                ", totalTimeCrowdControlDealt=" + totalTimeCrowdControlDealt +
                ", wardsKilled=" + wardsKilled +
                ", wardsPlaced=" + wardsPlaced +
                ", firstBloodParticipation=" + firstBloodParticipation +
                ", firstInhibitorParticipation=" + firstInhibitorParticipation +
                ", firstTowerParticipation=" + firstTowerParticipation +
                ", creepsPerMinDeltas=" + creepsPerMinDeltas +
                ", csDiffPerMinDeltas=" + csDiffPerMinDeltas +
                ", goldPerMinDeltas=" + goldPerMinDeltas +
                '}';
    }
}
