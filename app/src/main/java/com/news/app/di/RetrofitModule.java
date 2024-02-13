package com.news.app.di;

import com.news.app.BuildConfig;
import com.news.app.data.Constants;
import com.news.app.data.remote.MockInterCeptor;
import com.news.app.data.remote.NewsService;
import com.news.app.data.remote.WebServiceInterface;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {

    @Provides
    @Singleton
    public NewsService provideNewsService(
            OkHttpClient okHttpClient,
            GsonConverterFactory gsonConverterFactory
    ) {
        return provideRetrofit(okHttpClient, gsonConverterFactory, NewsService.class);
    }

    private  <T extends WebServiceInterface> T provideRetrofit(
            OkHttpClient okHttpClient,
            GsonConverterFactory gsonConverterFactory,
            Class<T> serviceInterface
    ) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(serviceInterface);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(
            @Named("MockInterceptor") Interceptor mockInterceptor
    ) {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        if (BuildConfig.MOCK_API.equalsIgnoreCase("true")) {
            client.addInterceptor(mockInterceptor);
        }
        return client.build();
    }

    @Singleton
    @Provides
    public GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    @Named("MockInterceptor")
    public Interceptor provideMockInterCeptor() {
        return new MockInterCeptor();
    }


}
