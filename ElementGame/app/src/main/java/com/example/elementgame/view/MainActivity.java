package com.example.elementgame.view;

import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.elementgame.R;
import com.example.elementgame.controller.ElementController;
import com.example.elementgame.model.datatypes.DraggableElement;
import com.example.elementgame.model.datatypes.DraggableObject;
import com.example.elementgame.model.datatypes.Element;
import com.example.elementgame.model.types.TaskType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ElementActivity {
    @BindView(R.id.area1) LinearLayout area1;
    @BindView(R.id.area2) LinearLayout area2;
    @BindView(R.id.area3) LinearLayout area3;
    @BindView(R.id.area4) LinearLayout area4;
    @BindView(R.id.area5) LinearLayout area5;
    @BindView(R.id.area6) LinearLayout area6;
    @BindView(R.id.sourceLayout) LinearLayout sourceLayout;

    private ArrayList<Element> elementList;
    private ArrayList<DraggableObject> draggableObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initActivity();
    }

    protected void initActivity() {
        ElementController.getInstance().startLoadingTasks(this);
        elementList = new ArrayList<>();

        setupExampleObjects();

        sourceLayout.setOnDragListener(new DragListener());
        area1.setOnDragListener(new DragListener());
        area2.setOnDragListener(new DragListener());
        area3.setOnDragListener(new DragListener());
        area4.setOnDragListener(new DragListener());
        area5.setOnDragListener(new DragListener());
        area6.setOnDragListener(new DragListener());
    }

    @Override
    public void UpdateOnTaskFinished(TaskType taskType, Object data) {
        try {
            switch (taskType){
                case READ_ELEMENTS:
                    if(data instanceof ArrayList<?>) {
                        elementList = (ArrayList<Element>) data;
                        setupExampleObjects();
                    }
                    break;
            }
        }
        catch (Exception ex){
            Log.e(this.getClass().getSimpleName(), String.format("Failed to process results of finished task: %s", ex.getMessage()));
        }
    }

    private void setupExampleObjects() {
        if(elementList == null || elementList.isEmpty()){
            return;
        }

        draggableObjects = new ArrayList<>();
        for (Element element : elementList) {
            draggableObjects.add(draggableObjects.size(), new DraggableElement(this, element, draggableObjects.size()));
        }

        for (DraggableObject obj : draggableObjects) {
            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(getAndroidDrawable(obj.getDrawable()));
            imageView.setPadding(10, 0, 10, 0);
            imageView.setId(obj.getID());
            imageView.setOnClickListener(new ClickListener());
            imageView.setOnLongClickListener(new LongClickListener());
            sourceLayout.addView(imageView);
        }
    }

    private Drawable getAndroidDrawable(int id){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(id);
        }

        return getDrawable(id);
    }

    private void createAlertDialog(String title, String message, String positiveButtonText, String negativeButtonText){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        if(negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createOptionAlertDialog(String title, String[] options){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
            }
        }).setTitle(title);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private DraggableObject getDraggableObjectOfArea(LinearLayout area){
        if(area.getChildCount() == 1){
            View view = area.getChildAt(0);
            int viewID = view.getId();
            return draggableObjects.get(viewID);
        }
        return null;
    }

    private class LongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(data, shadowBuilder, view, 0);
            } else {
                view.startDrag(data, shadowBuilder, view, 0);
            }
            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int viewID = view.getId();
            DraggableElement draggableElement = (DraggableElement) draggableObjects.get(viewID);

            createAlertDialog("Element Info", draggableElement.getDialogInformation(), "Back", null);
        }
    }

    private class DragListener implements View.OnDragListener{
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            View dragView = (View) dragEvent.getLocalState();
            int viewID = dragView.getId();
            DraggableObject draggableObject = draggableObjects.get(viewID);

            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.i("DragAction", String.format("ACTION_DRAG_STARTED: %s for %s \n", view.getTag(), draggableObject.Name));
                    //do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i("DragAction",String.format("ACTION_DRAG_ENTERED: %s for %s \n", view.getTag(), draggableObject.Name));
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("DragAction",String.format("ACTION_DRAG_EXITED: %s for %s \n", view.getTag(), draggableObject.Name));
                    //do nothing
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i("DragAction",String.format("ACTION_DROP: %s for %s \n", view.getTag(), draggableObject.Name));
                    LinearLayout oldParent = (LinearLayout) dragView.getParent();
                    LinearLayout newParent = (LinearLayout)view;

                    dragView.setVisibility(View.VISIBLE);
                    if(newParent.getChildCount() < 1 || newParent == sourceLayout){
                        oldParent.removeView(dragView);
                        newParent.addView(dragView);
                    }
                    else {
                        if(newParent != oldParent){
                            DraggableObject destinationObject = getDraggableObjectOfArea(newParent);
                            if(destinationObject != null){
                                String[] options = {"Attack", "Combine"};
                                createOptionAlertDialog(String.format("What do you want to do with %s on %s", draggableObject.Name, destinationObject.Name), options);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Problem getting draggable object", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i("DragAction",String.format("ACTION_DRAG_ENDED: %s for %s \n", view.getTag(), draggableObject.Name));
                default:
                    break;
            }
            return true;
        }
    }
}
