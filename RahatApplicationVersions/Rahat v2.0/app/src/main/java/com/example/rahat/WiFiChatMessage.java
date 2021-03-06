package com.example.rahat;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ruben on 04.12.17.
 */

public class WiFiChatMessage {

    private String TAG = "##WiFiChatMessage";
    private String text;
    private boolean owned;
    private String sender;
    private String time;

    public WiFiChatMessage(String text, boolean owned, String sender, String time) {
        this.text = text;
        this.owned = owned;
        this.sender = sender;
        this.time = time;
    }

    public WiFiChatMessage(String string) {
        try {
            JSONObject json = new JSONObject(string);
            this.text = (String) json.get("text");
            this.owned = (boolean) json.get("owned");
            this.sender = (String) json.get("sender");
            this.time = (String) json.get("time");

        } catch (JSONException e) {
            Log.d(TAG, "couldn't parse JSON string: " + e.getMessage());
        }
    }


    public String getText() {
        return text;
    }

    public boolean isOwned() {
        return owned;
    }

    public String getSender() {
        return sender;
    }

    public String getTime() {return time;}

    public String getJSONString(String sender) {
        String string;
        try {
            JSONObject json = new JSONObject()
                    .put("text", text)
                    .put("owned", false)
                    .put("sender", sender)
                    .put("time", time);
            string = json.toString();
        } catch (JSONException e) {
            Log.d(TAG, "couldn't construct JSONObject: " + e.getMessage());
            string = "";
        }
        return string;
    }
}
