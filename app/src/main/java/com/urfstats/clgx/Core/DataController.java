package com.urfstats.clgx.Core;

import com.urfstats.clgx.Android.GamesGetter;
import com.urfstats.clgx.LoLData.Game;
import com.urfstats.clgx.LoLData.URFStatistics;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
