package com.example.vishal.newsapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by VISHAL on 6/24/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {


    ItemClickListener listener;

    private String[] mainNewsData;
    private ArrayList<NewsItem> newsArrayList;

    public NewsAdapter(ArrayList<NewsItem> newsArrayList, ItemClickListener listener ) {
        this.newsArrayList = newsArrayList;
        this.listener = listener;
    }

    public NewsAdapter(ArrayList<NewsItem> newsList) {
        this.newsArrayList = newsList;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_rows, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        NewsItem newsItem = newsArrayList.get(position);

        holder.newsTitle.setText("TITLE: " +newsItem.getTitle());
        holder.newsDescription.setText("DESCRIPTION: " +newsItem.getDescription());
        holder.newsPublishedAt.setText("PUBLISHED AT: " +newsItem.getPublishedAt());

    }

    @Override
    public int getItemCount() {
        return newsArrayList.size() ;
    }

     class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView newsAuthor;
        public TextView newsTitle;
        public TextView newsDescription;
        public TextView newsUrl;
        public TextView newsPublishedAt;


        public ItemHolder(View itemView) {
            super(itemView);


            newsTitle = (TextView) itemView.findViewById(R.id.newsTitle);
            newsTitle.setTypeface(null, Typeface.BOLD);
            newsDescription = (TextView) itemView.findViewById(R.id.newsDescription);
            newsPublishedAt = (TextView) itemView.findViewById(R.id.newsPublishedAt);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onItemClick(position);

        }
    }

    public void setNewsData(String[] newsData) {
        mainNewsData = newsData;
        notifyDataSetChanged();
    }
}

