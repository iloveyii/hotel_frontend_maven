package org.hotel.models;

public class Customer {

    public int id;
    public String email;
    public String phone;

    Customer(int id, String email, String phone) {
        this.id = id;
        this.email = email;
        this.phone = phone;
    }
}
