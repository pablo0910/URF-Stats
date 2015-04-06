package com.urfstats.clgx.LoLData;

import org.json.JSONException;
import org.json.JSONObject;

public class Participant {

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
    private ParticipantTimelineData assistedLaneKDDeltas;

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
            this.csDiffPerMinDeltas = new ParticipantTimelineData(timeline.getJSONObject("csDiffPerMinDeltas"));
            this.goldPerMinDeltas = new ParticipantTimelineData(timeline.getJSONObject("goldPerMinDeltas"));
            this.assistedLaneKDDeltas = new ParticipantTimelineData(timeline.getJSONObject("assistedLaneKillsPerMinDeltas"), timeline.getJSONObject("assistedLaneDeathsPerMinDeltas"));

        } catch (JSONException e) {

            System.err.println("Error: "+e);

        }

    }

    private boolean iParticipate(boolean first, boolean second) {

        return first || second;

    }

    private class ParticipantTimelineData {

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

        public ParticipantTimelineData(JSONObject data1, JSONObject data2) {

            this.tenToTwenty = -1;
            this.thirtyToEnd = -1;
            this.twentyToThirty = -1;
            this.zeroToTen = -1;

            try {

                if (data1.has("zeroToTen")) this.zeroToTen = data1.getDouble("zeroToTen") / data2.getDouble("zeroToTen");
                if (data1.has("tenToTwenty")) this.tenToTwenty = data1.getDouble("tenToTwenty") / data2.getDouble("tenToTwenty");
                if (data1.has("twentyToThirty")) this.twentyToThirty = data1.getDouble("twentyToThirty") / data2.getDouble("twentyToThirty");
                if (data1.has("thirtyToEnd")) this.thirtyToEnd = data1.getDouble("thirtyToEnd") / data2.getDouble("thirtyToEnd");

            } catch (JSONException e) {

                System.err.println("Error: "+e);

            }

        }

    }


}
