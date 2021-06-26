package com.example.internetnews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {
    //Step 0 -> Initialising some variables for our base class Articles.
    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Step - 1 Place where the layout gets linked to the adapter
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item,parent,false);
        return new NewsRVAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {
        //Step 3 -> Now we need to put some data into each item. get() will give us current position of the item.
        Articles articles = articlesArrayList.get(position);
        //holder is a object of VH which is helping us to getting the individual element to be populated with data.
        holder.subTitleTv.setText(articles.getDescription());
        holder.titleTV.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        //In order to add a dedicated screen for a particular news article.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("title", articles.getTitle());
                intent.putExtra("content", articles.getContent());
                intent.putExtra("desc", articles.getDescription());
                intent.putExtra("image", articles.getUrlToImage());
                intent.putExtra("url", articles.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        //Simply returning the array list.
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Step 2 -> Creating a VH class and basically is to provide a backbone to the UI of individual Item of RecyclerView.
        private TextView titleTV, subTitleTv;
        private ImageView newsIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.idTVNewsHeading);
            subTitleTv = itemView.findViewById(R.id.idTVSubTitle);
            newsIV = itemView.findViewById(R.id.idIVNewsDetails);
        }
    }
}


