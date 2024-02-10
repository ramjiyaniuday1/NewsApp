package com.news.app.domain.model;

import com.google.gson.annotations.SerializedName;
import com.news.app.data.model.ArticleDto;

import java.util.ArrayList;
import java.util.List;

public class Articles {
    public List<Article> articles;

    public Articles(List<Article> articles) {
        this.articles = articles;
    }

}
