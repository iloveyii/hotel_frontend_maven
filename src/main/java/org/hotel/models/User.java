package org.hotel.models;

import org.json.JSONObject;

public class User {
    private int id;
    private String email;
    private String password;

    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String toString() {
        return String.format("%d, %s, %s", id, email, password);
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public JSONObject toJson() {
        JSONObject user = new JSONObject();
        user.put("email", this.email); user.put("password", this.password);
        return user;
    }
}
