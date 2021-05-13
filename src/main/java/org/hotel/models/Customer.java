package org.hotel.models;

import org.json.JSONObject;

public class Customer {

    private int id;
    private String name;
    private String email;
    private String phone;

    public Customer(int id, String email, String phone) {
        this.id = id;
        this.name = "abc";
        this.email = email;
        this.phone = phone;
    }

    public JSONObject toJson() {
        JSONObject customer = new JSONObject();
        customer.put("email", this.email); customer.put("phone", this.phone);
        return customer;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
