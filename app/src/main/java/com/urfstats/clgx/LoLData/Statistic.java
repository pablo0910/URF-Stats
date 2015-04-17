package com.urfstats.clgx.LoLData;

/*
*   Class Made by pablo0910 - 2015
*   GitHub: https://github.com/pablo0910
*   Mail: pablo0910@hotmail.com
*   Class Done for Riot Api Challenge 2015
*
*   This class stores the Game which verifies the Statistic
*
 */


import java.io.Serializable;

public class Statistic implements Serializable {

    public Game game;
    public int lastCheckedIndex;

    public Statistic() {

        lastCheckedIndex = 0;

    }

}
