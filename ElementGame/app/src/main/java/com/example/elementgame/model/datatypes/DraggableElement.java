package com.example.elementgame.model.datatypes;

import android.content.Context;

public class DraggableElement extends DraggableObject {
    public Element element;

    public DraggableElement(Context context, Element element, int draggableID){
        super(draggableID, element.Name, context.getResources().getIdentifier(element.getImageID(), "drawable", context.getPackageName()));
        this.element = element;
    }

    public int getTier(){
        return element.getTier();
    }
}
