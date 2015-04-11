package com.urfstats.clgx.LoLData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class URFStatistics implements Serializable {

    private ArrayList<Statistic> statistics;
    int mostBannedChamp = 67;
    int lessBannedChamp = 17;
    private ArrayList<Game> games;
    private ArrayList<Integer> bans;
    private int lastGamesIdBansCheck = 0;

    private final int MOSTKILLSMATCH = 0;
    private final int LESSKILLSMATCH = 1;
    private final int MOSTDEATHSMATCH = 2;
    private final int LESSDEATHSMATCH = 3;
    private final int MOSTGOLDEARNED = 4;
    private final int LESSGOLDEARNED = 5;
    private final int LARGESTCRIT = 6;
    private final int MOSTMINIONSKILLED = 7;
    private final int LESSMINIONSKILLED = 8;
    private final int MOSTPENTAKILLS = 9;
    private final int MOSTDMGTOCHAMPS = 10;
    private final int MOSTCC = 11;
    private final int MOSTWARDSPLACED = 12;
    private final int LESSWARDSPLACED = 13;
    private final int MOSTBARONKILLS = 14;
    private final int LONGESTMATCH = 15;
    private final int SHORTESTMATCH = 16;
    private final int MOSTBANNEDCHAMP = 17;
    private final int LESSBANNEDCHAMP = 18;

    private final int TOTALSTATS = 17;

    public URFStatistics(ArrayList<Game> games) {

        statistics = new ArrayList<>();
        bans = new ArrayList<>();
        lastGamesIdBansCheck = 0;
        this.games = games;

    }

    public void allStatsCalculator() {

        killsMatch();
        deathsMatch();
        goldEarnedMatch();
        largestCritMatch();
        minionsKilledMatch();
        mostPentaKillsMatch();
        mostDamageToChampsMatch();
        mostCrowdControlMatch();
        wardsPlacedMatch();
        mostBaronKillsMatch();
        durationMatch();
        bannedChampStat();

    }

    private void killsMatch() {

        int id = MOSTKILLSMATCH;
        int idL = LESSKILLSMATCH;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalKills() < game.totalKills()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.totalKills() > game.totalKills()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            Statistic statL = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);
                statL.game = games.get(0);

            }
            statistics.add(id, stat);
            statistics.add(idL, statL);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalKills() < game.totalKills()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.totalKills() > game.totalKills()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        }

    }

    private void deathsMatch() {

        int id = MOSTDEATHSMATCH;
        int idL = LESSDEATHSMATCH;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalDeaths() < game.totalDeaths()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.totalDeaths() > game.totalDeaths()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            Statistic statL = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);
                statL.game = games.get(0);

            }
            statistics.add(id, stat);
            statistics.add(idL, statL);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalDeaths() < game.totalDeaths()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.totalDeaths() > game.totalDeaths()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        }

    }

    private void goldEarnedMatch() {

        int id = MOSTGOLDEARNED;
        int idL = LESSGOLDEARNED;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.goldEarned() < game.goldEarned()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.goldEarned() > game.goldEarned()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            Statistic statL = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);
                statL.game = games.get(0);

            }
            statistics.add(id, stat);
            statistics.add(idL, statL);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.goldEarned() < game.goldEarned()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.goldEarned() > game.goldEarned()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        }

    }

    private void largestCritMatch() {

        int id = LARGESTCRIT;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.largestCriticalStrike() < game.largestCriticalStrike()) {

                    statistics.get(id).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);

            }
            statistics.add(id, stat);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.largestCriticalStrike() < game.largestCriticalStrike()) {

                    statistics.get(id).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;

            }

        }

    }

    private void minionsKilledMatch() {

        int id = MOSTMINIONSKILLED;
        int idL = LESSMINIONSKILLED;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.minionsKilled() < game.minionsKilled()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.minionsKilled() > game.minionsKilled()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            Statistic statL = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);
                statL.game = games.get(0);

            }
            statistics.add(id, stat);
            statistics.add(idL, statL);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.minionsKilled() < game.minionsKilled()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.minionsKilled() > game.minionsKilled()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        }

    }

    private void mostPentaKillsMatch() {

        int id = MOSTPENTAKILLS;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalPentaKills() < game.totalPentaKills()) {

                    statistics.get(id).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);

            }
            statistics.add(id, stat);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalPentaKills() < game.totalPentaKills()) {

                    statistics.get(id).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;

            }

        }

    }

    private void mostDamageToChampsMatch() {

        int id = MOSTDMGTOCHAMPS;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalDamageToChamps() < game.totalDamageToChamps()) {

                    statistics.get(id).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);

            }
            statistics.add(id, stat);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalDamageToChamps() < game.totalDamageToChamps()) {

                    statistics.get(id).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;

            }

        }

    }

    private void mostCrowdControlMatch() {

        int id = MOSTCC;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalCrowdControl() < game.totalCrowdControl()) {

                    statistics.get(id).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);

            }
            statistics.add(id, stat);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalCrowdControl() < game.totalCrowdControl()) {

                    statistics.get(id).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;

            }

        }

    }

    private void wardsPlacedMatch() {

        int id = MOSTWARDSPLACED;
        int idL = LESSWARDSPLACED;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalWardsPlaced() < game.totalWardsPlaced()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.totalWardsPlaced() > game.totalWardsPlaced()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            Statistic statL = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);
                statL.game = games.get(0);

            }
            statistics.add(id, stat);
            statistics.add(idL, statL);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalWardsPlaced() < game.totalWardsPlaced()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.totalWardsPlaced() > game.totalWardsPlaced()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        }

    }

    private void mostBaronKillsMatch() {

        int id = MOSTBARONKILLS;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalBaronKills() < game.totalBaronKills()) {

                    statistics.get(id).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);

            }
            statistics.add(id, stat);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.totalBaronKills() < game.totalBaronKills()) {

                    statistics.get(id).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;

            }

        }

    }

    private void durationMatch() {

        int id = LONGESTMATCH;
        int idL = SHORTESTMATCH;

        if (statistics.size() == TOTALSTATS) {

            for (int i=statistics.get(id).lastCheckedIndex; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.getMatchDuration() < game.getMatchDuration()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.getMatchDuration() > game.getMatchDuration()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        } else {

            Statistic stat = new Statistic();
            Statistic statL = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);
                statL.game = games.get(0);

            }
            statistics.add(id, stat);
            statistics.add(idL, statL);

            for (int i=1; i < games.size(); i++) {

                Game game = games.get(i);

                if (statistics.get(id).game.getMatchDuration() < game.getMatchDuration()) {

                    statistics.get(id).game = game;

                }

                if (statistics.get(idL).game.getMatchDuration() > game.getMatchDuration()) {

                    statistics.get(idL).game = game;

                }

                statistics.get(id).lastCheckedIndex = i;
                statistics.get(idL).lastCheckedIndex = i;

            }

        }

    }

    private void bannedChampStat() {

        //int id = MOSTBANNEDCHAMP;
        //int idL = LESSBANNEDCHAMP;

        int numAppearancesMostBanned = 0;
        int numAppearancesLessBanned = 0;

        for (int i = lastGamesIdBansCheck; i < games.size(); i++) {

            Game game = games.get(i);

            bans.addAll(game.getGameBans());

        }

        Collections.sort(bans);

        int numAppearancesChamp = 0;
        int oldChamp = -1;
        for (Integer champId: bans) {

            if (champId != oldChamp) {

                if (numAppearancesChamp > numAppearancesMostBanned) mostBannedChamp = champId;
                if (numAppearancesChamp < numAppearancesLessBanned) lessBannedChamp = champId;
                numAppearancesChamp = 0;

            } else {

                numAppearancesChamp++;

            }

            oldChamp = champId;

        }


    }

}
