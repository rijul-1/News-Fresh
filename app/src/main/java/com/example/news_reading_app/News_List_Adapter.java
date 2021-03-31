package com.example.news_reading_app;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class News_List_Adapter extends RecyclerView.Adapter<NewsViewHolder> {

    NewsItemClicked mylistener;

    public News_List_Adapter( NewsItemClicked listener) {
     mylistener = listener;
    }

    ArrayList<news> list = new ArrayList<news>();

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        NewsViewHolder viewholder = new NewsViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylistener.onItemClicked(list.get(viewholder.getAdapterPosition()));
            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        news currentItem = list.get(position);
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.titleview.setText(currentItem.getNews_title());
        holder.author.setText("Source: "+ currentItem.getNews_author());

        Glide.with(holder.itemView.getContext()).load(currentItem.getNews_imageurl()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public void update(ArrayList<news> updated_news){
        list.clear();
        list.addAll(updated_news);
        notifyDataSetChanged();
    }
}

class NewsViewHolder extends  RecyclerView.ViewHolder{
    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

    }
    ProgressBar progressBar = itemView.findViewById(R.id.progressBar);
    TextView titleview = itemView.findViewById(R.id.title);
    ImageView image = itemView.findViewById(R.id.News_Image);
    TextView author = itemView.findViewById(R.id.Author);
}

interface  NewsItemClicked{
    public default void onItemClicked(news item){

    }
}