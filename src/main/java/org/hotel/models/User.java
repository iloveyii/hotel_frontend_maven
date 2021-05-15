package org.hotel.models;

import org.json.JSONObject;

public class User {
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

    public JSONObject toJson() {
        JSONObject user = new JSONObject();
        if(id != 0) {
            user.put("id", id);
        }
        user.put("name", this.name); user.put("email", this.email); user.put("password", this.password);
        return user;
    }
}
