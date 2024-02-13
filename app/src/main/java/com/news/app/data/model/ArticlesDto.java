package com.news.app.data.model;

import com.google.gson.annotations.SerializedName;
import com.news.app.domain.model.Articles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticlesDto {
    @SerializedName("articles")
    public List<ArticleDto> articleDtos;

    public ArticlesDto(List<ArticleDto> articleDtos) {
        this.articleDtos = articleDtos;
    }
    public Articles mapToArticles() {
        return new Articles(articleDtos.stream().map(ArticleDto::mapToArticle).collect(Collectors.toList()));
    }
}
