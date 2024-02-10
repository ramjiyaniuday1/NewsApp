package com.news.app.domain;

import com.news.app.domain.model.Articles;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

public interface NewsRepository {

    public @NonNull Observable<Articles> getNews();

    Articles filterNews(String query);

}
