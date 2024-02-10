package com.news.app.data.remote;

import com.news.app.data.model.ArticlesDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface NewsService extends WebServiceInterface {

    @GET("news")
    Observable<ArticlesDto> getNews();

}
