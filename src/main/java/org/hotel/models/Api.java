package org.hotel.models;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Api {
    private static String url = "http://localhost:8090/api/v1/";

    public static String getApiData(String endpoint){
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url + endpoint).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                String response = "";
                Scanner scanner = new Scanner(connection.getInputStream());
                while(scanner.hasNextLine()){
                    response += scanner.nextLine();
                    response += "\n";
                }
                scanner.close();
                return response;
            } else {
                System.out.printf("Status code is other than 200 :: %d \n",  responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        // an error happened
        return null;
    }


    public static String postApiData(String endpoint, String data){
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url + endpoint).openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            String urlParameters  = "username=root@admin.com&password=root123";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            /* try( DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
                wr.write( postData );
            } */

            try(OutputStream os = connection.getOutputStream()) {
                os.write(postData, 0, postData.length);
            }

            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                String response = "";
                Scanner scanner = new Scanner(connection.getInputStream());
                while(scanner.hasNextLine()){
                    response += scanner.nextLine();
                    response += "\n";
                }
                scanner.close();
                return response;
            } else {
                System.out.printf("Status code is other than 200 :: %d \n",  responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        // an error happened
        return null;
    }
}
