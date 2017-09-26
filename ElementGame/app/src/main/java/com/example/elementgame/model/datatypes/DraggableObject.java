package com.example.elementgame.model.datatypes;

public class DraggableObject {
    public int getID() {
        return ID;
    }

    public int getDrawable() {
        return DrawableID;
    }

    private int ID;
    public String Name;
    private int DrawableID;

    public DraggableObject(int id, String name, int drawableID){
        this.ID = id;
        this.Name = name;
        this.DrawableID = drawableID;
    }
}
