package com.news.app.data.repo;

import com.news.app.data.remote.NewsService;
import com.news.app.domain.NewsRepository;
import com.news.app.domain.model.Articles;

import java.util.Collections;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class NewsRepoImpl implements NewsRepository {
    private final NewsService newsService;

    private Articles cachedArticles;
    @Inject
    public NewsRepoImpl(NewsService newsService) {
        this.newsService = newsService;
    }
    public @NonNull Observable<Articles> getNews() {
        return newsService.getNews()
                .map(articlesDto -> {
                     Collections.sort(articlesDto.articleDtos, (o1, o2) -> o2.getPublishedAt().compareTo(o1.getPublishedAt()));
                     return articlesDto.mapToArticles();
                })
                .map(articles -> {
                    if (articles.articles.isEmpty() && cachedArticles != null) {
                        return cachedArticles;
                    } else {
                        cachedArticles = articles;
                        return articles;
                    }
                })
                .flatMap(articles -> Observable.just(articles));
    }

    @Override
    public Articles filterNews(String query) {
        if (cachedArticles != null) {
            if (query.isEmpty()) {
                return cachedArticles;
            } else {
                Articles filteredArticles = new Articles(cachedArticles.articles.stream()
                        .filter(article -> article.title.toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList()));
                return filteredArticles;
            }

        }
        return null;
    }

}
