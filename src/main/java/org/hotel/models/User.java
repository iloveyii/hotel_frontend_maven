package org.hotel.models;

import org.json.JSONObject;

import java.util.HashMap;

public class User extends Model {
    private int id;
    private String name;
    private String email;
    private String password;

    public User(int id,String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String toString() {
        return String.format("%d, %s, %s", id, email, password);
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
    public String getPassword() {
        return password;
    }
    public String setPassword(String pass) {
        return password = pass;
    }

    public JSONObject toJson() {
        JSONObject user = new JSONObject();
        if(id != 0) {
            user.put("id", id);
        }
        user.put("name", this.name); user.put("email", this.email); user.put("password", this.password);
        return user;
    }

    @Override
    public HashMap rules() {
        HashMap<String, String> r = new HashMap<String, String>();
        r.put("name", "string|required");
        r.put("email", "email|required");
        r.put("password", "required|min:3|max:20");
        return r;
    }
}
