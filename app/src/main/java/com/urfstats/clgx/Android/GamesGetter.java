package com.urfstats.clgx.Android;

/*
*   Class Made by pablo0910 - 2015
*   GitHub: https://github.com/pablo0910
*   Mail: pablo0910@hotmail.com
*   Class Done for Riot Api Challenge 2015
*
*   This class is the service which retrieves Game Info.
*
 */

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.urfstats.clgx.LoLData.Game;
import com.urfstats.clgx.LoLData.URFStatistics;
import com.urfstats.clgx.R;
import com.urfstats.clgx.Utilities.RiotApiConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class GamesGetter extends Service {

    public static final String GAMESFILENAME = "/games.bin";
    public static final String STATSFILENAME = "/stats.bin";
    private long start;
    private ArrayList<Game> games = new ArrayList<>();
    private URFStatistics stats = new URFStatistics(games);
    private Date date;
    private Date beginDate;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    private PendingIntent pendingIntent;
    private AlarmManager manager;

    public void onCreate() { }

    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle dateContainer = intent.getExtras();
        beginDate = (Date) dateContainer.getSerializable("date1");
        date = (Date) dateContainer.getSerializable("date2");
        GamesGetter.GetMatchesInfo matchesInfo = new GetMatchesInfo();
        matchesInfo.execute();
        stopSelf();
        return START_NOT_STICKY;

    }

    public void saveData() {

        File file = new File(this.getFilesDir(), GAMESFILENAME);
        ObjectOutputStream oos;
        try {

            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            oos.writeObject(games);
            oos.close();

            file = new File(this.getFilesDir(), STATSFILENAME);
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            oos.writeObject(stats);
            oos.close();

        } catch(Exception e) {

            System.err.println("Error! Data not saved! " +e);

        }

    }

    public void loadData() {

        File file = new File(this.getFilesDir(), GAMESFILENAME);
        if (file.exists()) {

            ObjectInputStream ois;
            try {

                ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
                games = (ArrayList<Game>) ois.readObject();
                ois.close();

                file = new File(this.getFilesDir(), STATSFILENAME);
                ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
                stats = (URFStatistics) ois.readObject();
                ois.close();

            } catch (Exception e) {

                System.err.println("Error! Data not loaded! " + e);
                games = new ArrayList<>();

            }

        }

    }

    private void notificationManager() {

        mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("LoL Matches Retrieve")
                .setContentText("Download in progress...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true);

        Intent resultIntent = new Intent(this, StatsListActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

    }

    private Date getProperDate(Date old) { //Calculates a proper data for API

        Date newDate;
        long start = old.getTime() / 300000;
        start = start * 300000;
        newDate = new Date(start);

        return newDate;

    }

    private void getMatches() {

        loadData();
        beginDate = getProperDate(beginDate);
        long difference = date.getTime() - beginDate.getTime() + 300000;

        notificationManager();
        while(beginDate.compareTo(date) <= 0) {

            Long diff = (date.getTime() - beginDate.getTime()) * 100 / difference;
            int tempDiff = 100 - diff.intValue();

            mBuilder.setContentText("Download in progress... "+tempDiff+'%')
                    .setProgress(100, tempDiff, false);
            // Displays the progress bar for the first time.
            mNotifyManager.notify(1010101, mBuilder.build());

            RiotApiConnection connection = new RiotApiConnection("euw.api.pvp.net/api/lol/euw/v4.1/game/ids?beginDate="+(beginDate.getTime()/1000));
            connection.sendGet();

            switch (connection.responseCode) {

                case 200:

                    try {

                        JSONArray all = new JSONArray(connection.valueReturn);

                        for (int i=0; i < all.length(); i++) {

                            RiotApiConnection con = new RiotApiConnection("euw", "api.pvp.net/api/lol/euw/v2.2/match/"+all.get(i));
                            con.sendGet();
                            Game g1;
                            if (con.valueReturn!=null) {

                                g1 = new Game(new JSONObject(con.valueReturn));
                                games.add(g1);

                            }

                        }


                    } catch (JSONException e) {
                        System.err.println("Error! "+e);
                    }
                    break;

                case 429:
                    System.out.println("Requests Limit Exeed");
                    /*try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long newTime = beginDate.getTime()/1000 - 300;
                    beginDate = new Date(newTime * 1000); //We try again*/

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
                    System.out.println("Critical Error! MAN (:");
                    System.out.println(connection.valueReturn);
                    break;

            }

            long newTime = beginDate.getTime()/1000 + 300;
            beginDate = new Date(newTime * 1000);

        }
        mBuilder.setContentText("Download in progress... "+"100"+'%')
                .setOngoing(false)
                .setProgress(100, 100, false);
        // Displays the progress bar for the first time.
        mNotifyManager.notify(1010101, mBuilder.build());
        // When the loop is finished, updates the notification
        mBuilder.setContentText("Download complete")
                // Removes the progress bar
                .setProgress(0, 0, false);
        mNotifyManager.notify(1010101, mBuilder.build());

    }

    private class GetMatchesInfo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... id) {

            getMatches();

            return null;

        }

        @Override
        protected void onPostExecute(String dummy) {

            stats.allStatsCalculator();
            saveData();
            Toast.makeText(getApplicationContext(), "New Data Added!. Stats Updated!",
                    Toast.LENGTH_LONG).show();

            Intent refeshDataIntent = new Intent(getApplicationContext(), StatsListActivity.class);
            refeshDataIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (StatsListActivity.ACTIVITYALIVE) {

                startActivity(refeshDataIntent);
                StatsListActivity.DATAREADY = true;

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
