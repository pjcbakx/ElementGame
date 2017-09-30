package com.example.elementgame.model.datatypes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Element {
    private String ID;
    public String Name;
    private String ImageID;
    private int Tier;

    public String getID() {
        return ID;
    }

    public String getImageID() {
        return ImageID;
    }

    public int getTier() {
        return Tier;
    }

    public Element(String id, String name, int tier, String imageID){
        this.ID = id;
        this.Name = name;
        this.Tier = tier;
        this.ImageID = imageID;
    }

    public Element(JSONObject jsonObject){
        fillFromJson(jsonObject);
    }

    private void fillFromJson(JSONObject jsonObject){
        try {
            ID = jsonObject.getString("id");
            Name = jsonObject.getString("name");
            Tier = jsonObject.getInt("tier");
            ImageID = jsonObject.isNull("image") ? "unknown_element" : jsonObject.getString("image");
        }
        catch (JSONException ex){
            Log.e(this.getClass().getSimpleName(), String.format("Failed get Element info from JSON: %s", ex.getMessage()));
        }
    }
}
