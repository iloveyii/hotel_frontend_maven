package org.hotel.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Helper {

    public static JSONObject toJsonObject(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject;
        }catch (JSONException err){
            System.out.println("Error" + err.toString());
        }
        return null;
    }

    public static boolean isStatusTrue(String response) {
        JSONObject respJson = new JSONObject(response);
        return  (boolean)respJson.get("status");
    }
}
