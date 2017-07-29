package com.example.vishal.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.vishal.newsapp.Database.Contract;
import com.squareup.picasso.Picasso;


/**
 * Created by VISHAL on 6/24/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{


    ItemClickListener listener;
    private Cursor cursor;
    private Context context;

    NewsAdapter(Cursor cursor, ItemClickListener listener)
    {
        this.cursor=cursor;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_rows, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
       public void onBindViewHolder(ItemHolder holder, final int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount() ;
    }

     class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

          TextView newsTitle;
          TextView newsDescription;
          TextView newsPublishedAt;
          TextView newsUrl;
          ImageView newsUrlToImage;
          TextView newsAuthor;


        public ItemHolder(View itemView) {
            super(itemView);

            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsDescription =itemView.findViewById(R.id.newsDescription);
            newsPublishedAt = itemView.findViewById(R.id.newsPublishedAt);
            newsUrlToImage = itemView.findViewById(R.id.newsUrlToImage);
            itemView.setOnClickListener(this);
        }



        public void bind(int position)
        {
            cursor.moveToPosition(position);

            // USED PICASO API TO LOAD THE IMAGE FROM THE URL TO IMAGE LINK.

            newsTitle.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWSFEED.COLUMN_TITLE)));
            newsDescription.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWSFEED.COLUMN_DESCRIPTION)));
            newsPublishedAt.setText("PUBLISHED AT: "+cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWSFEED.COLUMN_PUBLISHED_AT)));
            String urlToImage = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWSFEED.COLUMN_URL_TO_IMAGE));
            Picasso.with(context).load(urlToImage).into(newsUrlToImage);

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onItemClick(cursor,position);
        }
    }
}

