package org.hotel.models;

import org.json.JSONObject;


public class Booking {
    private int id;
    private String room_number;
    private double price;
    private String customer_name;
    private String customer_email;
    private String datetime;

    public Booking(int id, String room_number, double price, String customer_name, String customer_email, String datetime) {
        this.id = id;
        this.room_number = room_number;
        this.price = price;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.datetime = datetime;
    }

    public String toString() {
        return String.format("%d, %s, %.2f, %s, %s, %s", id, room_number, price, customer_name, customer_email, datetime);
    }

     public JSONObject toJson() {
        JSONObject booking = new JSONObject();
        if(id != 0) {
            booking.put("id", id);
        }
        booking.put("room_number", room_number); booking.put("price", price); booking.put("customer_name", customer_name);
        booking.put("customer_email", customer_email); booking.put("datetime", datetime);
        return booking;
    }

    public int getId() {
        return id;
    }

    public String getRoom_number() {
        return room_number;
    }

    public double getPrice() {
        return price;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getDatetime() {
        return datetime;
    }
}
