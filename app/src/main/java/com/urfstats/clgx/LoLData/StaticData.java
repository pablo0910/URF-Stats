package com.urfstats.clgx.LoLData;


import com.urfstats.clgx.APIConnection.RiotApiConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class StaticData {

    public static final HashMap<Integer, String> champs = new HashMap<>();

    public void getChamps() {

        RiotApiConnection con = new RiotApiConnection("global.api.pvp.net/api/lol/static-data/euw/v1.2/champion", 'd');
        con.sendGet();

        switch (con.responseCode) {

            case 200:

                try {

                    JSONObject all = new JSONObject(con.valueReturn);
                    JSONObject champData = all.getJSONObject("data");

                    Iterator iter = champData.keys();
                    while (iter.hasNext()) {
                        String key = (String) iter.next();
                        JSONObject runeCheck = new JSONObject(champData.getString(key));
                        try {
                            champs.put((Integer) runeCheck.get("id"), (String) runeCheck.get("name"));
                        } catch (JSONException e) {
                            // Something went wrong!
                        }
                    }

                    System.out.println(champs);


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

    }

}
