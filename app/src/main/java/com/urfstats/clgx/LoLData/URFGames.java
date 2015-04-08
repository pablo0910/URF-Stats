package com.urfstats.clgx.LoLData;

import android.os.AsyncTask;

import com.urfstats.clgx.Utilities.RiotApiConnection;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class URFGames implements Serializable {

    ArrayList<Game> games;

    public URFGames() {

        games = new ArrayList<>();
        URFGames.GetGame staticData = new GetGame();
        staticData.execute();

    }

    private class GetGame extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... id) {

            RiotApiConnection con = new RiotApiConnection("euw", "api.pvp.net/api/lol/euw/v2.2/match/2053309787");
            con.sendGet();

            switch (con.responseCode) {

                case 200:

                    try {

                        JSONObject all = new JSONObject(con.valueReturn);
                        Game game = new Game(all);
                        System.out.println(game);


                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;

                case 429:
                    System.out.println("Requests Limit Exeed");
                    break;

                case 404:
                    System.out.println("Query not found!");
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

            return null;

        }

        @Override
        protected void onPostExecute(String dummy) {

        }

    }

}
