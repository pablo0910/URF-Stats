package com.urfstats.clgx.Core;

/*
*   Class Made by pablo0910 - 2015
*   GitHub: https://github.com/pablo0910
*   Mail: pablo0910@hotmail.com
*   Class Done for Riot Api Challenge 2015
*
*   This class manages all the Application Info.
*
 */


import com.urfstats.clgx.Android.GamesGetter;
import com.urfstats.clgx.LoLData.Game;
import com.urfstats.clgx.LoLData.URFStatistics;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DataController {

    private ArrayList<Game> games;
    private URFStatistics stats;
    private String filesDir;
    public boolean dataToShow = false;

    public DataController(String dir) {

        filesDir = dir;
        loadData();

    }

    public void loadData() {

        File file = new File(filesDir+GamesGetter.STATSFILENAME);
        ObjectInputStream ois;
        try {

            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            stats = (URFStatistics) ois.readObject();
            ois.close();

            file = new File(filesDir+ GamesGetter.GAMESFILENAME);
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            games = (ArrayList<Game>) ois.readObject();
            ois.close();
            dataToShow = true;

        } catch(Exception e) {

            System.err.println("No Data Retrieved Yet!");

        }

    }

    public URFStatistics getStats() {
        return stats;
    }
}
