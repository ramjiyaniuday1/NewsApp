package com.news.app.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.news.app.domain.NewsRepository;
import com.news.app.domain.model.Articles;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private final NewsRepository newsRepository;
    final PublishSubject<String> subject = PublishSubject.create();
    MutableLiveData<Articles> newsData = new MutableLiveData<>();
    Articles articles;
    @Inject
    MainViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void init() {
        searchObservable();
    }
    private void setNews(Articles articles) {
        newsData.postValue(articles);
    }

    public void getNews() {
        newsRepository.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    setNews(result);
                }, Throwable::printStackTrace);
    }

    public void search(String query) {
        subject.onNext(query);
    }

    public void searchObservable() {

        subject.debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String result) {
                        setNews(newsRepository.filterNews(result));
                    }
                });
    }
}
