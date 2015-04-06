package com.urfstats.clgx.LoLData;

public class Participant {

    private int participantId;
    private int championId;
    private String highestAchievedSeasonTier;
    private int teamId;

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
    private ParticipantTimelineData assistedLaneKillsParticipationDeltas;

    private class ParticipantTimelineData {

        public double tenToTwenty;
        public double thirtyToEnd;
        public double twentyToThirty;
        public double zeroToTen;

    }


}
