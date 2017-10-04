package com.example.elementgame.controller;


import android.content.Context;
import android.util.Log;

import com.example.elementgame.model.Constant;
import com.example.elementgame.model.tasks.FileReader;
import com.example.elementgame.model.types.TaskType;

public class FileController {
    private static FileController instance;

    public static FileController getInstance() {
        if(instance == null) instance = new FileController();
        return instance;
    }

    public void ReadElementsTask(Context context) {
        FileReader.getInstance().StartJSONReadingTask(context, TaskType.READ_ELEMENTS, Constant.JSON_FILE_ELEMENTS, true);
    }

    public void ReadElementLevelsTask(Context context) {
        FileReader.getInstance().StartJSONReadingTask(context, TaskType.READ_ELEMENT_LEVELS, Constant.JSON_FILE_ELEMENT_LEVELS, true);
    }

    public void processReadTaskFinished(Context context, TaskType taskType, Object results){
        try {
            switch (taskType){
                case READ_ELEMENTS:
                case READ_ELEMENT_LEVELS:
                    ElementController.getInstance().processFinishedTask(context, taskType, results);
                    break;
            }
        }
        catch (Exception ex){
            Log.e(this.getClass().getSimpleName(), String.format("Failed to finish processing read fileName: %s", ex.getMessage()));
        }
    }
}
