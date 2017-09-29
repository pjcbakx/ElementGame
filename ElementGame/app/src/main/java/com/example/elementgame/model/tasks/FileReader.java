package com.example.elementgame.model.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.elementgame.controller.ElementController;
import com.example.elementgame.model.Constant;
import com.example.elementgame.model.datatypes.Element;
import com.example.elementgame.model.types.TaskType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FileReader {
    private static FileReader instance;

    public static FileReader getInstance() {
        if(instance == null) instance = new FileReader();
        return instance;
    }

    public void ReadElementsTask(Context context) {
        JSONReaderAsyncTask readElementsTask = new JSONReaderAsyncTask(context, TaskType.READ_ELEMENTS, Constant.JSON_FILE_ELEMENTS, true);
        readElementsTask.execute();
    }

    private String loadJSONFromAsset(InputStream stream) {
        String json;

        try {
            InputStream is = stream;
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void processReadTaskFinished(Context context, TaskType taskType, Object results){
        try {
            switch (taskType){
                case READ_ELEMENTS:
                    ElementController.getInstance().processFinishedTask(context, taskType, results);
                    break;
            }
        }
        catch (Exception ex){
            Log.e(this.getClass().getSimpleName(), String.format("Failed to finish processing read file: %s", ex.getMessage()));
        }
    }

    private class JSONReaderAsyncTask extends AsyncTask<String, Void, Object> {

        private Context context;
        private TaskType taskType;
        private String file;
        private String jsonDocument = null;
        private boolean isJsonArray;

        public JSONReaderAsyncTask(Context context, TaskType taskType, String file, boolean isJsonArray) {
            this.context = context;
            this.taskType = taskType;
            this.file = file;
            this.isJsonArray = isJsonArray;
        }

        protected void onPostExecute(Object results) {
            processReadTaskFinished(context, taskType, results);
        }

        @Override
        protected Object doInBackground(String... params) {
            Object data = null;
            Object rawFileData = readFromFile();
            if(rawFileData != null){
                data = processRawFileData(rawFileData);
            }

            return data;
        }

        private Object readFromFile(){
            try {
                jsonDocument = loadJSONFromAsset(context.getAssets().open(file));
                if(isJsonArray){
                    return new JSONArray(jsonDocument);
                }
                else {
                    return new JSONObject(jsonDocument);
                }
            } catch (Exception ex) {
                Log.e(this.getClass().getSimpleName(), String.format("Exception reading file: %s", ex.getMessage()));
            }

            return null;
        }

        private Object processRawFileData(Object rawFileData){
            Object processedFileData = null;

            try {
                switch (taskType) {
                    case READ_ELEMENTS:
                        ArrayList<Element> elements = new ArrayList<>();
                        JSONArray array = (JSONArray) rawFileData;
                        for (int i = 0; i < array.length(); i++) {
                            Element element = new Element(context, array.getJSONObject(i));
                            elements.add(element);
                        }

                        processedFileData = elements;
                        break;
                }
            }
            catch (Exception ex){
                Log.e(this.getClass().getSimpleName(), String.format("Failed to finish processing raw read file data: %s", ex.getMessage()));
            }

            return processedFileData;
        }
    }
}
