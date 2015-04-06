package com.urfstats.clgx.APIConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RiotApiConnection {

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

    public RiotApiConnection(String web) {

        url = "https://"+web+APIGLOBAL;

    }

    public RiotApiConnection(String web, char dummy) {

        url = "https://"+web+USERAPI;

    }

    // HTTP GET request
    public void sendGet() {

        try {

            //System.out.println(url);
            URL obj = new URL(url.replaceAll(" ", "%20"));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            responseCode = con.getResponseCode(); //Código de respuesta
            //System.out.println("\nSending 'GET' request to URL : " + url);
            //System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            //System.out.println(response.toString());

            valueReturn = response.toString();


        } catch (Exception e) {
            System.err.println("Error getting web" + e);
        }

    }

}
