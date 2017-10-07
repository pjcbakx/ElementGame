package com.example.elementgame.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elementgame.R;
import com.example.elementgame.model.datatypes.ElementLevel;

import java.io.Serializable;
import java.util.ArrayList;


public class ElementLevelAdapter extends RecyclerView.Adapter<ElementLevelAdapter.ElementLevelHolder> {

    private Context context;
    private ArrayList<ElementLevel> elementLevels;
    private OnItemClickListener listener;


    public ElementLevelAdapter(Context context, ArrayList<ElementLevel> elementLevels, OnItemClickListener listener) {
        this.context = context;
        this.elementLevels = elementLevels;
        this.listener = listener;
    }

    @Override
    public ElementLevelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, null);
        return new ElementLevelHolder(layout);
    }

    @Override
    public void onBindViewHolder(ElementLevelHolder holder, int position) {
        ElementLevel elementLevel = elementLevels.get(position);
        holder.bind(elementLevel, context.getResources().getIdentifier(elementLevels.get(position).getIconID(), "drawable", context.getPackageName()), listener);
    }

    @Override
    public int getItemCount() {
        return elementLevels.size();
    }

    public static class ElementLevelHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ElementLevelHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }

        public void bind(final ElementLevel item, int imageID, final OnItemClickListener listener) {
            textView.setText(item.getName());
            imageView.setImageResource(imageID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener extends Serializable{
        void onItemClick(ElementLevel item);
    }

}
