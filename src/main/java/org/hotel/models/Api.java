package org.hotel.models;

import org.json.JSONObject;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class Api {
    private static String url = "http://localhost:8090/api/v1/";
    private static HttpURLConnection connection = null;

    private static void setConnection(String method, String endpoint) {
        try {
            connection = (HttpURLConnection) new URL(url + endpoint).openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setRequestProperty("User-Agent", "Java client");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        }  catch (
            ConnectException e) {
                System.out.println("Cannot connect to backend server");
            } catch (MalformedURLException e) {
                System.out.println("Cannot connect to backend server");
            } catch (IOException e) {
                System.out.println("Cannot connect to backend server");
            } finally {
                // connection.disconnect();
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
            System.out.println("getJsonData " + response);
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
        return "";
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
        } catch (ConnectException e) {
            System.out.println("Cannot connect to backend server");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        // an error happened
        return "";
    }

    private static String getResponseData() {
        StringBuilder content = null;

        try (var br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            content = new StringBuilder();
            while ((line = br.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        } catch (MalformedURLException e) {
            System.out.println("Cannot connect to backend server");
        } catch (IOException e) {
            System.out.println("Cannot connect to backend server");
        } finally {
            connection.disconnect();
        }
        return content.toString();
    }

    public static String postApiData(String endpoint, JSONObject data) {
        try {
            setConnection("POST", endpoint);
            OutputStream os = connection.getOutputStream();
            os.write(data.toString().getBytes("UTF-8"));
            int responseCode = connection.getResponseCode();
            System.out.println("Status code:" + responseCode);
            if(responseCode == 200) {
                System.out.println("Status code is 200");
                return getResponseData();
            } else {
                System.out.println("Status code is other than 200." + responseCode);
            }
        } catch (MalformedURLException e) {
            System.out.println("Cannot connect to backend server");
        } catch (IOException e) {
            System.out.println("Cannot connect to backend server");
        } finally {
            connection.disconnect();
        }
        return "";
    }

    public static String deleteApiData(String endpoint, Integer id) {
        try {
            if(id == null) return "";
            setConnection("DELETE", endpoint + "/" + String.valueOf(id));
            int responseCode = connection.getResponseCode();
            System.out.println("Status code:" + responseCode);
            if(responseCode == 200) {
                System.out.println("Status code is 200");
                return getResponseData();
            } else {
                System.out.println("Status code is other than 200." + responseCode);
            }
        } catch (MalformedURLException e) {
            System.out.println("Cannot connect to backend server");
        } catch (IOException e) {
            System.out.println("Cannot connect to backend server");
        } finally {
            connection.disconnect();
        }
        return "";
    }
}
