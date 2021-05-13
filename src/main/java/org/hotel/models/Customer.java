package org.hotel.models;

import org.json.JSONObject;

public class Customer {

    public int id;
    public String email;
    public String phone;

    public Customer(int id, String email, String phone) {
        this.id = id;
        this.email = email;
        this.phone = phone;
    }

    public JSONObject toJson() {
        JSONObject customer = new JSONObject();
        customer.put("email", this.email); customer.put("phone", this.phone);
        return customer;
    }
}
