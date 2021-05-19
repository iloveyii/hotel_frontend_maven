package org.hotel.models;

import org.json.JSONObject;

import java.util.HashMap;

public class Customer extends Model {

    private int id;
    private String name;
    private String email;
    private String phone;

    public Customer(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public JSONObject toJson() {
        JSONObject customer = new JSONObject();
        if(id != 0) {
            customer.put("id", id);
        }
        customer.put("name", this.name); customer.put("phone", this.phone); customer.put("email", this.email);
        return customer;
    }

    public String toString() {
        return String.format("id:%d, name:%s, phone:%s, email:%s", id, name, phone, email);
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

    @Override
    public HashMap<String, String> rules() {
        HashMap<String, String> c = new HashMap<String, String>();
        c.put("name", "string|required");
        c.put("email", "email|required");
        c.put("phone", "required|min:3|max:20");
        return c;
    }
}
