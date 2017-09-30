package com.example.elementgame.model.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.elementgame.model.types.TaskType;

public class FileWriter {
    private static FileWriter instance;

    public static FileWriter getInstance() {
        if(instance == null) instance = new FileWriter();
        return instance;
    }

    private void processReadTaskFinished(Context context, TaskType taskType, Object results){
        try {
            switch (taskType){
            }
        }
        catch (Exception ex){
            Log.e(this.getClass().getSimpleName(), String.format("Failed to finish processing writing file: %s", ex.getMessage()));
        }
    }

    private class JSONWriterAsyncTask extends AsyncTask<String, Void, Object> {

        private Context context;
        private TaskType taskType;
        private String fileName;

        public JSONWriterAsyncTask(Context context, TaskType taskType, String fileName) {
            this.context = context;
            this.taskType = taskType;
            this.fileName = fileName;
        }

        protected void onPostExecute(Object results) {
            processReadTaskFinished(context, taskType, results);
        }

        @Override
        protected Object doInBackground(String... params) {
            return null;
        }
    }
}
