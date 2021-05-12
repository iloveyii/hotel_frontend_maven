package org.hotel.models;

public class Room {
    private int id;
    private String number;
    private double price;
    private String booked;

    Room(int id, String number, double price, String booked) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.booked = booked;
    }

    public String toString() {
        return String.format("%d, %s, %.2f, %s", id, number, price, booked);
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
}
