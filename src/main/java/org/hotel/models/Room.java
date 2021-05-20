package org.hotel.models;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;


public class Room extends Model {
    private int id;
    private String number;
    private double price;
    private String booked;

    public Room(int id, String number, double price, String booked) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.booked = booked;
    }

    public String toString() {
        return String.format("%d, %s, %.2f, %s", id, number, price, booked);
    }

    public boolean isBooked() {
        System.out.printf("isBooked : %s", booked.toLowerCase());
        return booked.toLowerCase().equals("yes");
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getPrice() {
        return price;
    }

    public String getBooked() {
        return booked;
    }

    public String setBooked(String status) {
        return booked = status;
    }

    public JSONObject toJson() {
        JSONObject room = new JSONObject();
        if(id != 0) {
            room.put("id", id);
        }
        room.put("number", this.number); room.put("price", this.price); room.put("booked", this.booked);
        return room;
    }

    @Override
    public HashMap<String, String> rules() {
        HashMap<String, String> r = new HashMap<String, String>();
        r.put("number", "string|required");
        r.put("price", "number|required");
        r.put("booked", "required|string|min:2|max:3");
        return r;
    }
}
