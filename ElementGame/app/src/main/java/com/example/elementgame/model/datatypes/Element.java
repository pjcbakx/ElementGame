package com.example.elementgame.model.datatypes;

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
}
