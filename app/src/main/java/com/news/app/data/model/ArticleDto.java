package com.news.app.data.model;

import com.google.gson.annotations.SerializedName;
import com.news.app.domain.model.Article;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleDto implements Serializable {
    @SerializedName("publishedAt")
    public Date publishedAt;
    @SerializedName("title")
    public String title;
    @SerializedName("url")
    public String url;
    @SerializedName("urlToImage")
    public String urlToImage;

    public ArticleDto(Date publishedAt, String title, String url, String urlToImage) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
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

    public Article mapToArticle() {
        String stringDate = getFormattedDate(publishedAt);
        return new Article("published: "+stringDate, publishedAt, title, url, urlToImage);
    }

    private String getFormattedDate(Date date) {
        String pattern = "HH:mm, dd MMM yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
}

