package com.urfstats.clgx.Android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.urfstats.clgx.Core.DataController;
import com.urfstats.clgx.LoLData.Game;
import com.urfstats.clgx.LoLData.URFStatistics;
import com.urfstats.clgx.Utilities.RiotApiConnection;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GamesGetter extends Service {

    private final String FILENAME = "servicedata.bin";
    private long start;
    private ArrayList<Game> games = new ArrayList<>();
    Date date;
    Date beginDate;

    Boolean amIRunningAgain;

    public void onCreate() { amIRunningAgain = false; }

    public int onStartCommand(final Intent intent1, int flags, int startId) {

        date = new Date(Calendar.getInstance().getTimeInMillis());
        loadData();
        beginDate = new Date(start);
        amIRunningAgain = true;

        while(beginDate.compareTo(date) <= 0) {

            RiotApiConnection connection = new RiotApiConnection("euw.api.pvp.net/api/lol/euw/v4.1/game/ids?beginDate="+(beginDate.getTime()/1000));

            switch (connection.responseCode) {

                case 200:

                    try {

                        JSONObject all = new JSONObject(connection.valueReturn);


                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;

                case 429:
                    System.out.println("Requests Limit Exeed");
                    break;

                case 404:
                    break;

                case 400:
                case 401:
                    break;

                case 500:
                case 503:
                    System.out.println("Couldn't Connect to RIOT API");
                    break;

                default:
                    System.out.println("Critical Error!");
                    break;

            }

            long newTime = beginDate.getTime()/1000 + 300;
            beginDate = new Date(newTime * 1000);

        }
        return START_REDELIVER_INTENT;

    }



    public void saveData() {

        File file = new File(this.getFilesDir(), this.FILENAME);
        ObjectOutputStream oos;
        try {

            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            oos.writeLong(this.start);
            oos.close();

        } catch(Exception e) {

            System.err.println("Error! Data not saved! " +e);

        }

        file = new File(this.getFilesDir(), DataController.FILENAME);
        try {

            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            oos.writeObject(games);
            oos.close();

        } catch(Exception e) {

            System.err.println("Error! Data not saved! " +e);

        }

    }

    public void loadData() {

        File file = new File(this.getFilesDir(), this.FILENAME);
        if (file.exists()) {

            ObjectInputStream ois;
            try {

                ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
                this.start = ois.readLong();
                ois.close();

            } catch (Exception e) {

                System.err.println("Error! Data not loaded! " + e);
                this.start = 1427873100000L;

            }

        } else {

            this.start = 1427873100000L;

        }

        file = new File(this.getFilesDir(), DataController.FILENAME);

        if (file.exists()) {
            ObjectInputStream ois;
            try {

                ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
                games = (ArrayList<Game>) ois.readObject();
                ois.close();

            } catch (Exception e) {

                System.err.println("Error! Data not loaded! " + e);

            }

        }

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +1000, restartServicePI);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
