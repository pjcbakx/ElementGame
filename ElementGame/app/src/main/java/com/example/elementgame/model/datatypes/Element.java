package com.example.elementgame.model.datatypes;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Element {
    private String ID;
    public String Name;
    private int AssetID;
    private int Tier;

    public String getID() {
        return ID;
    }

    public int getAssetID() {
        return AssetID;
    }

    public int getTier() {
        return Tier;
    }

    public Element(String id, String name, int tier, int assetID){
        this.ID = id;
        this.Name = name;
        this.Tier = tier;
        this.AssetID = assetID;
    }

    public Element(Context context, JSONObject jsonObject){
        fillFromJson(context, jsonObject);
    }

    private void fillFromJson(Context context, JSONObject jsonObject){
        try {
            ID = jsonObject.getString("id");
            Name = jsonObject.getString("name");
            Tier = jsonObject.getInt("tier");

            AssetID = context.getResources().getIdentifier(jsonObject.getString("image"), "drawable", context.getPackageName());
            if(AssetID == 0){
                AssetID = context.getResources().getIdentifier("unknown_element", "drawable", context.getPackageName());
            }
        }
        catch (JSONException ex){
            Log.e(this.getClass().getSimpleName(), String.format("Failed get Element info from JSON: %s", ex.getMessage()));
        }
    }
}
