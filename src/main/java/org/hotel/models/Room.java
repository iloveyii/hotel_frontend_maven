package org.hotel.models;

public class Room {
    public int id;
    public String number;
    public double price;
    public String booked;

    Room(int id, String number, double price, String booked) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.booked = booked;
    }

    public String toString() {
        return String.format("%d, %s, %.2f, %s", id, number, price, booked);
    }

}
