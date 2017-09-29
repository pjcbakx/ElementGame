package com.example.elementgame.controller;

import android.content.Context;

import com.example.elementgame.model.datatypes.Element;
import com.example.elementgame.model.tasks.FileReader;
import com.example.elementgame.model.types.TaskType;
import com.example.elementgame.view.ElementActivity;

import java.util.ArrayList;

public class ElementController {
    private static ElementController instance;

    private ArrayList<Element> elements;

    public static ElementController getInstance() {
        if(instance == null) instance = new ElementController();
        return instance;
    }

    public void startLoadingTasks(Context context){
        FileReader.getInstance().ReadElementsTask(context);
    }

    public void processFinishedTask(Context context, TaskType taskType, Object result){
        switch (taskType){
            case READ_ELEMENTS:
                elements = (ArrayList<Element>) result;
                if(context instanceof ElementActivity){
                    ((ElementActivity)context).UpdateOnTaskFinished(taskType, elements);
                }
                break;
        }
    }

    public Element getElement(String elementID){
        for (Element element : elements) {
            if(element.getID().equals(elementID)){
                return element;
            }
        }

        return null;
    }

    public ArrayList<Element> getElements(ArrayList<String> elementIDs){
        ArrayList<Element> elements = new ArrayList<>();

        for (String elementID : elementIDs) {
            Element element = getElement(elementID);
            if(element != null){
                elements.add(element);
            }
        }

        return elements;
    }
}
