package com.example.elementgame.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elementgame.R;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.RecyclerViewHolder> {

    private Context context;
    private int[] images;
    private String[] names;

    public MyRecyclerAdapter(Context context, int[] images, String[] names) {
        this.context = context;
        this.images = images;
        this.names = names;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, null);
        RecyclerViewHolder holder = new RecyclerViewHolder(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.textView.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
