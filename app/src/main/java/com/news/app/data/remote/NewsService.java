package com.news.app.data.remote;

import com.news.app.data.model.ArticlesDto;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface NewsService extends WebServiceInterface {

    @GET("news")
    Single<ArticlesDto> getNews();

}
