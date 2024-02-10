package com.news.app.di;

import com.news.app.data.remote.NewsService;
import com.news.app.data.repo.NewsRepoImpl;
import com.news.app.domain.NewsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public NewsRepository provideNewsRepository(
            NewsService newsService
    ) {
        return new NewsRepoImpl(newsService);
    }
}
