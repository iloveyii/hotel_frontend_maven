package org.hotel.models;

import javafx.css.Style;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data {
    public ArrayList<Room> rooms = new ArrayList<>();
    public ArrayList<Customer> customers = new ArrayList<>();
    public Customer currentCustomer = null;
    public Room currentRoom = null;

    public Data() throws IOException {
        Room r1 = new Room(1, "111", 250, "no");
        Room r2 = new Room(2, "112", 252, "yes");
        Room r3 = new Room(3, "113", 350, "no");
        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);

        /*Customer c1 = new Customer(1, "111-222-333", "ali1@yahoo.com");
        Customer c2 = new Customer(2, "112-222-333", "ali2@yahoo.com");
        Customer c3 = new Customer(3, "113-222-333", "ali3@yahoo.com");
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);*/

        // Fetch rooms from api

        /*Room newRoom  =  new Room(0, "1234", 99.9, "Yes");
        String roomsStringAdd = Api.postApiData("rooms", newRoom.toJson());
        System.out.println("roomsStringAdd:::" + roomsStringAdd); */
        loadRoomsData();
        loadCustomersData();
    }

    public void loadRoomsData() {
        String roomsString = Api.getApiData("rooms");
        if(roomsString.length() > 0) {
            rooms.clear();
            addToRooms(roomsString);
        }
    }

    public void loadCustomersData() {
        // Fetch customers from api
        String customersString = Api.getApiData("customers");
        if(customersString.length() > 0) {
            customers.clear();
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
            str += String.format("%d, %s, %s \n", c.getId(), c.getPhone(), c.getEmail());
        }
        return  str;
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
