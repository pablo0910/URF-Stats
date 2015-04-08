package com.urfstats.clgx.LoLData;

import java.io.Serializable;

public class Statistic implements Serializable {

    public Game game;
    public int lastCheckedIndex;

    public Statistic() {

        lastCheckedIndex = 0;

    }

}
