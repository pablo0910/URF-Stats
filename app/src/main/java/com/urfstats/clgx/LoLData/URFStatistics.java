package com.urfstats.clgx.LoLData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class URFStatistics implements Serializable {

    private ArrayList<Statistic> statistics;
    private ArrayList<Game> games;

    private final int CRAZIESTMATCH = 0;
    private final int LESSCRAZYMATCH = 1;

    public URFStatistics(ArrayList<Game> games) {

        statistics = new ArrayList<>();
        this.games = games;

    }

    public void craziestMatch() {

        int id = CRAZIESTMATCH;
        int idL = LESSCRAZYMATCH;

        System.out.println(id+"|"+idL);

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

            System.out.println(statistics.get(id).game);
            System.out.println(statistics.get(idL).game);

        }

    }

}
