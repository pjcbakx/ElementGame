package com.example.elementgame.model.datatypes;

import android.util.Log;

import com.example.elementgame.controller.ElementController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ElementLevel implements Serializable {
    public String getName() {
        return Name;
    }

    public String getIconID() {
        return IconID;
    }

    private String Name;
    private String IconID;
    private ArrayList<Element> ResourceElements;
    private Element GoalElement;

    public ElementLevel(JSONObject jsonObject){
        fillFromJson(jsonObject);
    }

    private void fillFromJson(JSONObject jsonObject){
        try {
            Name = jsonObject.getString("name");
            IconID = jsonObject.isNull("icon") ? "unknown_element" : jsonObject.getString("icon");

            String goalElementID  = jsonObject.getString("goal_element");
            GoalElement = ElementController.getInstance().getElement(goalElementID);

            ResourceElements = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("resource_elements");
            for(int i = 0; i < jsonArray.length(); i++){
                String resourceElementID = jsonArray.getString(i);
                ResourceElements.add(ElementController.getInstance().getElement(resourceElementID));
            }
        }
        catch (JSONException ex){
            Log.e(this.getClass().getSimpleName(), String.format("Failed get Element info from JSON: %s", ex.getMessage()));
        }
    }
}
