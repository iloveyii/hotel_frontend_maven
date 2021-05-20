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
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Customer> customers = new ArrayList<>();
    public ArrayList<Booking> bookings = new ArrayList<>();
    public Customer currentCustomer = null;
    public Room currentRoom = null;
    public User currentUser = null;
    public Booking currentBooking = null;

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
        loadBookingsData();
        loadCustomersData();
        loadUsersData();
    }

    public void loadBookingsData() {
        String bookingsString = Api.getApiData("bookings");
        if(bookingsString.length() > 0) {
            bookings.clear();
            addToBookings(bookingsString);
        }
    }

    public void loadRoomsData() {
        String roomsString = Api.getApiData("rooms");
        if(roomsString.length() > 0) {
            rooms.clear();
            addToRooms(roomsString);
        }
    }

    public void loadUsersData() {
        String usersString = Api.getApiData("users");
        if(usersString.length() > 0) {
            users.clear();
            addToUsers(usersString);
        }
    }

    public void loadCustomersData() {
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

    private void addToBookings(String responseString) {
        JSONObject response = new JSONObject(responseString);
        boolean success = (boolean) response.getBoolean("success");

        if(success == true) {
            JSONArray _bookings = new JSONArray(response.getJSONArray("data"));
            int id; double price; String room_number, name, phone, email, datetime;
            for(int i=0; i < _bookings.length(); i++) {
                JSONObject booking = _bookings.getJSONObject(i);
                id = (int) booking.getInt("id");
                room_number = (String) booking.getString("room_number");
                price = booking.getDouble("price");
                name = (String) booking.getString("name");
                phone = (String) booking.getString("phone");
                email = (String) booking.getString("email");
                datetime = (String) booking.getString("datetime");
                Booking b = new Booking(id, room_number, price, name, phone, email, datetime);
                System.out.println(b);
                bookings.add(b);
            }
        } else {
            System.out.println("Response status from endpoint /rooms is false");
        }
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
                // Refresh the current room also
                if(currentRoom != null && (currentRoom.getId() == id)) {
                    currentRoom = r;
                }
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
            int id; String name, phone, email;
            for(int i=0; i < _customers.length(); i++) {
                JSONObject room = _customers.getJSONObject(i);
                id = (int) room.getInt("id");
                name = (String) room.getString("name");
                phone = (String) room.getString("phone");
                email = room.getString("email");
                Customer c = new Customer(id, name, phone, email);
                System.out.println(c);
                customers.add(c);
            }
        } else {
            System.out.println("Response status / success is false from endpoint /customers");
        }
    }

    private void addToUsers(String responseString) {
        JSONObject response = new JSONObject(responseString);
        boolean success = (boolean) response.getBoolean("success");

        if(success == true) {
            JSONArray _users = new JSONArray(response.getJSONArray("data"));
            int id; String name, email, password;
            for(int i=0; i < _users.length(); i++) {
                JSONObject user = _users.getJSONObject(i);
                id = (int) user.getInt("id");
                name = (String) user.getString("name");
                email = (String) user.getString("email");
                password = user.getString("password");
                User u = new User(id, name, email, password);
                System.out.println(u);
                users.add(u);
            }
        } else {
            System.out.println("Response status from endpoint /users is false");
        }
    }

}
