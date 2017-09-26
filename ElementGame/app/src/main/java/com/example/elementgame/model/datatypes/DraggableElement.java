package com.example.elementgame.model.datatypes;

public class DraggableElement extends DraggableObject {
    public Element element;

    public DraggableElement(Element element, int dragableID){
        super(dragableID, element.Name, element.getAssetID());
        this.element = element;
    }

    public int getTier(){
        return element.getTier();
    }
}
