package com.innovexa.apiintro;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.myViewHolder> {
    Context context;
    List<UserModel> dataList;

    public NewsAdapter(Context context, List<UserModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.newsTitle.setText(dataList.get(position).getTitle());
        holder.publishedDate.setText(dataList.get(position).getPubDate());

        String imageUrl = dataList.get(position).getImage_url();
        Glide.with(context).load(imageUrl).into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView newsTitle, publishedDate;
        ImageView newsImage;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            publishedDate = itemView.findViewById(R.id.publishedDate);
            newsImage = itemView.findViewById(R.id.newsImage);
        }
    }
}
