package com.example.elementgame.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.elementgame.R;
import com.example.elementgame.controller.ElementController;
import com.example.elementgame.model.datatypes.ElementLevel;
import com.example.elementgame.model.types.TaskType;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class SplashActivity extends ElementActivity {

    private ArrayList<ElementLevel> elementLevels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        initActivity();
    }

    @Override
    protected void initActivity() {
        ElementController.getInstance().startLoadingTasks(this);

        Thread logoTimer = new Thread() {
            public void run(){
                try{
                    while(elementLevels == null) {
                        int logoTimer = 0;
                        while (logoTimer < 2000) {
                            sleep(100);
                            logoTimer = logoTimer + 100;
                        }
                    }
                    Intent i = new Intent(SplashActivity.this, OptionActivity.class);
                    i.putExtra("Levels", elementLevels);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    finish();
                }
            }
        };
        logoTimer.start();
    }

    @Override
    public void UpdateOnTaskFinished(TaskType taskType, Object data) {
        try {
            switch (taskType){
                case READ_ELEMENTS:
                    ElementController.getInstance().startLoadingLevels(this);
                    break;
                case READ_ELEMENT_LEVELS:
                    if(data instanceof ArrayList<?>) {
                        elementLevels = (ArrayList<ElementLevel>) data;
                    }
                    break;
            }
        }
        catch (Exception ex){
            Log.e(this.getClass().getSimpleName(), String.format("Failed to process results of finished task: %s", ex.getMessage()));
        }


    }
}
