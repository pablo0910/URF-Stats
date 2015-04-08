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

    public final static String FILENAME = "/urfstats.bin";
    private ArrayList<Game> games;
    private URFStatistics stats;
    private String filesDir;

    public DataController(String dir) {

        filesDir = dir;
        loadData();
        saveData();

    }

    public void saveData() {

        File file = new File(filesDir+FILENAME);
        ObjectOutputStream oos;
        try {

            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            oos.writeObject(stats);
            oos.close();

        } catch(Exception e) {

            System.err.println("Error! Data not saved! " +e);

        }

    }

    public void loadData() {

        File file = new File(filesDir+FILENAME);
        ObjectInputStream ois;
        try {

            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            stats = (URFStatistics) ois.readObject();
            ois.close();

            file = new File(filesDir+ GamesGetter.FILENAME);
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            long dummy = ois.readLong();
            games = (ArrayList<Game>) ois.readObject();
            ois.close();

        } catch(Exception e) {

            System.err.println("Error! Data not loaded! " +e);
            games = new ArrayList<>();
            stats = new URFStatistics(games);

        }

    }

}
