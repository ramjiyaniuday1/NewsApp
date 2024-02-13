package com.news.app.ui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.news.app.LiveDataTestUtil;
import com.news.app.domain.NewsRepository;
import com.news.app.domain.model.Article;
import com.news.app.domain.model.Articles;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModelTest {


    @Mock
    private NewsRepository newsRepository;

    private MainViewModel mainViewModel;

    Articles articles;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws ParseException {
        MockitoAnnotations.initMocks(this);
        mainViewModel = new MainViewModel(newsRepository);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        articles = createArticleDto();
        mainViewModel.init();
    }

    @Test
    public void testGetNews() throws InterruptedException {
        when(newsRepository.getNews()).thenReturn(Observable.just(articles));

        mainViewModel.getNews();

        Articles result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.newsData);
        assertEquals(articles, result);
    }

    @Test
    public void testSearch() throws InterruptedException {
        String query = "Why Crypto Idealogues Won’t Touch";
        when(newsRepository.filterNews(query)).thenReturn(articles);

        mainViewModel.search(query);

        Articles result = LiveDataTestUtil.getOrAwaitValue(mainViewModel.newsData);
        assertEquals(articles, result);
    }

    @Test
    public void testLoadingState() throws InterruptedException {
        when(newsRepository.getNews()).thenReturn(Observable.just(articles));

        // Assert that loading is true before calling getNews
        assertEquals(true, mainViewModel.loading.getValue());

        mainViewModel.getNews();

        // Assert that loading is false after calling getNews
        Boolean loading = LiveDataTestUtil.getOrAwaitValue(mainViewModel.loading);
        assertEquals(false, loading);
    }

    private Articles createArticleDto() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Article article1 = new Article("2024-01-10T22:41:25Z", dateFormat.parse("2024-01-10T22:41:25Z"), "SEC approves bitcoin ETFs (for real this time)",
                "https://www.engadget.com/sec-approves-bitcoin-etfs-for-real-this-time-224125584.html",
                "https://www.engadget.com/sec-approves-bitcoin-etfs-for-real-this-time-224125584.html");

        Article article2 = new Article("2024-01-10T22:42:25Z", dateFormat.parse("2024-01-10T22:42:25Z"), "Why Crypto Idealogues Won’t Touch Bitcoin ETFs",
                "https://www.engadget.com/sec-approves-bitcoin-etfs-for-real-this-time-224125584.html",
                "https://www.engadget.com/sec-approves-bitcoin-etfs-for-real-this-time-224125584.html");

        return new Articles(Arrays.asList(article1, article2));
    }
}