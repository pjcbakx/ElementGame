package com.example.elementgame.model.datatypes;

import android.content.Context;

public class DraggableElement extends DraggableObject {
    private Element element;

    public DraggableElement(Context context, Element element, int draggableID){
        super(draggableID, element.Name, context.getResources().getIdentifier(element.getImageID(), "drawable", context.getPackageName()));
        this.element = element;
    }

    public String getDialogInformation(){
        return ("Name: " + element.Name) +
                "\n" +
                "Tier rank: " + element.getTier();
    }
}
