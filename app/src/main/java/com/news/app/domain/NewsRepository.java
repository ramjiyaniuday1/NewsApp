package com.news.app.domain;

import com.news.app.domain.model.Articles;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface NewsRepository {

    public @NonNull Single<Articles> getNews();

    Articles filterNews(String query);

}
