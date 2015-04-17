package com.urfstats.clgx.Utilities;

/*
*   Class Made by pablo0910 - 2015
*   GitHub: https://github.com/pablo0910
*   Mail: pablo0910@hotmail.com
*   Class Done for Riot Api Challenge 2015
*
*   Utility that connect the Application with Riot Api
*
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class RiotApiConnection implements Serializable {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String APIID = "4021313b-1cd7-43f1-94d4-667ee489d577";
    private final String APIGLOBAL = "&api_key="+APIID;
    private final String USERAPI = "?api_key="+APIID;
    public String url;
    public String valueReturn = null;
    public int responseCode = 0;

    public RiotApiConnection(String server, String web) {

        url = "https://"+server+'.'+web+USERAPI;

    }

    public RiotApiConnection(String server, String web, char dummy) {

        url = "https://"+server+'.'+web+APIGLOBAL;

    }

    public RiotApiConnection(String web) {

        url = "https://"+web+APIGLOBAL;

    }

    public RiotApiConnection(String web, char dummy) {

        url = "https://"+web+USERAPI;

    }

    // HTTP GET request
    public void sendGet() {

        try {

            URL obj = new URL(url.replaceAll(" ", "%20"));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("User-Agent", USER_AGENT);

            responseCode = con.getResponseCode(); //Return Code

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            valueReturn = response.toString();


        } catch (Exception e) {
            //System.err.println("Error getting web" + e);
        }

    }

}
