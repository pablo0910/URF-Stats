package com.urfstats.clgx.Android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;

import com.urfstats.clgx.LoLData.Game;
import com.urfstats.clgx.LoLData.StaticData;
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
import java.util.Calendar;
import java.util.Date;

public class GamesGetter extends Service {

    public static final String FILENAME = "/servicedata.bin";
    private long start;
    private ArrayList<Game> games = new ArrayList<>();
    Date date;
    Date beginDate;

    Boolean amIRunningAgain;

    public void onCreate() { amIRunningAgain = false; }

    public int onStartCommand(final Intent intent1, int flags, int startId) {

        GamesGetter.GetMatchesInfo matchesInfo = new GetMatchesInfo();
        matchesInfo.execute();
        return START_REDELIVER_INTENT;

    }



    public void saveData() {

        File file = new File(this.getFilesDir(), FILENAME);
        ObjectOutputStream oos;
        try {

            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            oos.writeLong(this.start);
            oos.writeObject(games);
            oos.close();

        } catch(Exception e) {

            System.err.println("Error! Data not saved! " +e);

        }

    }

    public void loadData() {

        File file = new File(this.getFilesDir(), FILENAME);
        if (file.exists()) {

            ObjectInputStream ois;
            try {

                ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
                this.start = ois.readLong();
                games = (ArrayList<Game>) ois.readObject();
                ois.close();

            } catch (Exception e) {

                System.err.println("Error! Data not loaded! " + e);
                this.start = 1427873100000L;
                games = new ArrayList<>();

            }

        } else {

            this.start = 1427873100000L;

        }

    }

    private void getMatches() {

        date = new Date(Calendar.getInstance().getTimeInMillis());
        loadData();
        beginDate = new Date(start);
        amIRunningAgain = true;

        while(beginDate.compareTo(date) <= 0) {

            RiotApiConnection connection = new RiotApiConnection("euw.api.pvp.net/api/lol/euw/v4.1/game/ids?beginDate="+(beginDate.getTime()/1000));
            connection.sendGet();

            switch (connection.responseCode) {

                case 200:

                    try {

                        JSONArray all = new JSONArray(connection.valueReturn);

                        for (int i=0; i < all.length(); i++) {

                            RiotApiConnection con = new RiotApiConnection("euw", "api.pvp.net/api/lol/euw/v2.2/match/"+all.get(i));
                            con.sendGet();
                            System.out.println(con.valueReturn);
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
        saveData();

    }

    private class GetMatchesInfo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... id) {

            getMatches();

            return null;

        }

        @Override
        protected void onPostExecute(String dummy) {

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
