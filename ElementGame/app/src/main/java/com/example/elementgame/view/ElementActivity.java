package com.example.elementgame.view;

import android.support.v7.app.AppCompatActivity;
import com.example.elementgame.model.types.TaskType;

public abstract class ElementActivity extends AppCompatActivity {
    protected abstract void initActivity();
    public abstract void UpdateOnTaskFinished(TaskType taskType, Object data);
}
