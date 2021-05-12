package org.hotel.models;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Api {
    private static String url = "http://localhost:8090/api/v1/";
    private static HttpURLConnection connection = null;

    private static void setConnection(String method, String endpoint) {
        try {
            connection = (HttpURLConnection) new URL(url + endpoint).openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);
        }  catch (
            ConnectException e) {
                System.out.println("Cannot connect to backend server");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
    }

    private  static String getJsonData() {
        String response = "";
        try {
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response += scanner.nextLine();
                response += "\n";
            }
            scanner.close();
            return response;
        } catch (ConnectException e) {
            System.out.println("Cannot connect to backend server");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }

    public static String getApiData(String endpoint){

        try {
            setConnection("GET", endpoint);
            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                return getJsonData();
            } else {
                System.out.println("Status code is other than 200.");
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
            setConnection("POST", "logins");
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
               return  getJsonData();
            } else {
                System.out.println("Status code is other than 200.");
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
