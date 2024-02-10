package com.news.app.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Article{
    public String publishedAt;
    public Date publishedAtDate;
    public String title;
    public String url;
    public String urlToImage;

    public Article(String publishedAt, Date publishedAtDate, String title, String url, String urlToImage) {
        this.publishedAt = publishedAt;
        this.publishedAtDate = publishedAtDate;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getPublishedAtDate() {
        return publishedAtDate;
    }

    public void setPublishedAtDate(Date publishedAtDate) {
        this.publishedAtDate = publishedAtDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

}

