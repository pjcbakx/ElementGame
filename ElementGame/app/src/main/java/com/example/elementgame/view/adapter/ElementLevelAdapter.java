package com.example.elementgame.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elementgame.R;
import com.example.elementgame.model.datatypes.ElementLevel;
import com.example.elementgame.view.LevelActivity;
import java.util.ArrayList;

interface ElementLevelHolderClickListener {
    void itemClicked(View v, ElementLevel item);
}

public class ElementLevelAdapter extends RecyclerView.Adapter<ElementLevelHolder> implements ElementLevelHolderClickListener {

    private Context context;
    private ArrayList<ElementLevel> elementLevels;


    public ElementLevelAdapter(Context context, ArrayList<ElementLevel> elementLevels) {
        this.context = context;
        this.elementLevels = elementLevels;
    }

    @Override
    public ElementLevelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, null);
        return new ElementLevelHolder(layout);
    }

    @Override
    public void onBindViewHolder(ElementLevelHolder holder, int position) {
        ElementLevel elementLevel = elementLevels.get(position);
        holder.bind(elementLevel, context.getResources().getIdentifier(elementLevels.get(position).getIconID(), "drawable", context.getPackageName()), this);
    }

    @Override
    public int getItemCount() {
        return elementLevels.size();
    }


    @Override
    public void itemClicked(View v, ElementLevel item) {
        Intent i = new Intent(context, LevelActivity.class);
        i.putExtra("Level", item);
        context.startActivity(i);
        ((Activity)context).overridePendingTransition(0, 0);
    }
}

class ElementLevelHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView textView;
    public ElementLevelHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        textView = (TextView) itemView.findViewById(R.id.textView);
    }

    public void bind(final ElementLevel item, int imageID, final ElementLevelHolderClickListener listener) {
        textView.setText(item.getName());
        imageView.setImageResource(imageID);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemClicked(view, item);
            }
        });
    }
}
