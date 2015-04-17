package com.urfstats.clgx.LoLData;

/*
*   Class Made by pablo0910 - 2015
*   GitHub: https://github.com/pablo0910
*   Mail: pablo0910@hotmail.com
*   Class Done for Riot Api Challenge 2015
*
*   This class stores some Static Data from LoL Api such as Name-Id Champions
*
 */


import com.urfstats.clgx.Utilities.RiotApiConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class StaticData implements Serializable {

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
