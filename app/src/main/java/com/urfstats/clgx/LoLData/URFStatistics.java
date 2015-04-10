package com.urfstats.clgx.LoLData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class URFStatistics implements Serializable {

    private ArrayList<Statistic> statistics;
    private HashMap<String, Integer> statisticId;
    private ArrayList<Game> games;

    public URFStatistics(ArrayList<Game> games) {

        statistics = new ArrayList<>();
        statisticId = new HashMap<>();
        this.games = games;

        statisticId.put("craziestMatch", 0);
        statisticId.put("lessCrazyMatch", 0);
        craziestMatch();

    }

    public void craziestMatch() {

        int id = statisticId.get("craziestMatch");
        int idL = statisticId.get("lessCrazyMatch");

        if (statistics.size() != 0) {

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

            System.out.println(statistics.get(id).game);
            System.out.println(statistics.get(idL).game);

        } else {

            Statistic stat = new Statistic();
            if (games.size() > 0) {

                stat.game = games.get(0);

            }
            statistics.add(id, stat);
            statistics.add(idL, stat);

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

            System.out.println(statistics.get(id).game);
            System.out.println(statistics.get(idL).game);

        }

    }

}
