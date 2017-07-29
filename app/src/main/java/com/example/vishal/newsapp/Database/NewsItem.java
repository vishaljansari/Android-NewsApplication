package com.example.vishal.newsapp.Database;

import java.util.ArrayList;

/**
 * Created by VISHAL on 6/24/2017.
 */

public class NewsItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private ArrayList<NewsItem> newsList;

    public NewsItem() {
    }

    public NewsItem(String author, String title, String description, String url, String urlToImage, String publishedAt) {
        super();
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }

    public NewsItem(ArrayList<NewsItem> newsList) {
        this.newsList = newsList;
    }

    public ArrayList<NewsItem> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<NewsItem> newsList) {
        this.newsList = newsList;
    }
}
