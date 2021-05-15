package org.hotel.models;

import org.json.JSONObject;


public class Booking {
    private int id;
    private String room_number;
    private double price;
    private String name;
    private String phone;
    private String email;
    private String datetime;

    public Booking(int id, String room_number, double price, String name, String phone, String email, String datetime) {
        this.id = id;
        this.room_number = room_number;
        this.price = price;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.datetime = datetime;
    }

    public String toString() {
        return String.format("%d, %s, %.2f, %s, %s, %s, %s", id, room_number, price, name, phone, email, datetime);
    }

     public JSONObject toJson() {
        JSONObject booking = new JSONObject();
        if(id != 0) {
            booking.put("id", id);
        }
        booking.put("room_number", room_number); booking.put("price", price); booking.put("name", name);
         booking.put("phone", phone); booking.put("email", email); booking.put("datetime", datetime);
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

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDatetime() {
        return datetime.substring(0, 10);
    }
}
