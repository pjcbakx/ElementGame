package com.example.elementgame.model.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.elementgame.controller.ElementController;
import com.example.elementgame.controller.FileController;
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

    public void StartJSONReadingTask(Context context, TaskType taskType, String fileName, boolean isJsonArray) {
        JSONReaderAsyncTask readElementsTask = new JSONReaderAsyncTask(context, taskType, fileName, isJsonArray);
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

    private class JSONReaderAsyncTask extends AsyncTask<String, Void, Object> {

        private Context context;
        private TaskType taskType;
        private String fileName;
        private boolean isJsonArray;

        public JSONReaderAsyncTask(Context context, TaskType taskType, String fileName, boolean isJsonArray) {
            this.context = context;
            this.taskType = taskType;
            this.fileName = fileName;
            this.isJsonArray = isJsonArray;
        }

        protected void onPostExecute(Object results) {
            FileController.getInstance().processReadTaskFinished(context, taskType, results);
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
                String jsonDocument = loadJSONFromAsset(context.getAssets().open(fileName));
                if(isJsonArray){
                    return new JSONArray(jsonDocument);
                }
                else {
                    return new JSONObject(jsonDocument);
                }
            } catch (Exception ex) {
                Log.e(this.getClass().getSimpleName(), String.format("Exception reading fileName: %s", ex.getMessage()));
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
                            Element element = new Element(array.getJSONObject(i));
                            elements.add(element);
                        }

                        processedFileData = elements;
                        break;
                }
            }
            catch (Exception ex){
                Log.e(this.getClass().getSimpleName(), String.format("Failed to finish processing raw read fileName data: %s", ex.getMessage()));
            }

            return processedFileData;
        }
    }
}
