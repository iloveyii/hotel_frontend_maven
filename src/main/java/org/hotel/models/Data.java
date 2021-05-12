package org.hotel.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    public ArrayList<Room> rooms = new ArrayList<>();
    public ArrayList<Customer> customers = new ArrayList<>();

    public Data() throws IOException {
        /* Room r1 = new Room(1, "111", 250, "no");
        Room r2 = new Room(2, "112", 252, "yes");
        Room r3 = new Room(3, "113", 350, "no");
        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3); */

        Customer c1 = new Customer(1, "111-222-333", "ali1@yahoo.com");
        Customer c2 = new Customer(2, "112-222-333", "ali2@yahoo.com");
        Customer c3 = new Customer(3, "113-222-333", "ali3@yahoo.com");
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);

        // Fetch rooms from api
        String roomsString = getApiData("rooms");
        if(roomsString.length() > 0) {
            addToRooms(roomsString);
        }
        // Fetch customers from api
        String customersString = getApiData("customers");
        if(customersString.length() > 0) {
            addToCustomers(customersString);
        }
    }

    public String toString() {
        String str = "\nRooms: \n";
        for(int i = 0; i < rooms.size(); i++) {
            Room r = rooms.get(i);
            str += String.format("%d, %s, %.2f, %s \n", r.getId(), r.getNumber(), r.getPrice(), r.getBooked());
        }

        str += "\nCustomers: \n";
        for(int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            str += String.format("%d, %s, %s \n", c.id, c.phone, c.email);
        }
        return  str;
    }

    private void api() {
        // Client client = ClientBuilder.newClient();
    }

    public String getApiData(String endpoint){
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL("http://localhost:8090/api/v1/"+endpoint).openConnection();
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
        }  catch (ConnectException e) {
            System.out.println("Cannot connect to backend server");
            return "";
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

    private void addToRooms(String responseString) {
        JSONObject response = new JSONObject(responseString);
        boolean success = (boolean) response.getBoolean("success");

        if(success == true) {
            JSONArray _rooms = new JSONArray(response.getJSONArray("data"));
            int id; String number; double price; String booked;
            for(int i=0; i < _rooms.length(); i++) {
                JSONObject room = _rooms.getJSONObject(i);
                id = (int) room.getInt("id");
                number = (String) room.getString("number");
                price = room.getDouble("price");
                booked = (String) room.getString("booked");
                Room r = new Room(id, number, price, booked);
                System.out.println(r);
                rooms.add(r);
            }
        } else {
            System.out.println("Response status from endpoint /rooms is false");
        }
    }

    private void addToCustomers(String customersString) {
        JSONObject response = new JSONObject(customersString);
        boolean success = response.getBoolean("success");

        if(success == true) {
            JSONArray _customers = new JSONArray(response.getJSONArray("data"));
            int id; String phone; String email;
            for(int i=0; i < _customers.length(); i++) {
                JSONObject room = _customers.getJSONObject(i);
                id = (int) room.getInt("id");
                phone = (String) room.getString("phone");
                email = room.getString("email");
                Customer c = new Customer(id, phone, email);
                System.out.println(c);
                customers.add(c);
            }
        } else {
            System.out.println("Response status / success is false from endpoint /customers");
        }
    }
}
