package com.news.app.data.repo;

import static org.mockito.Mockito.when;

import com.google.gson.JsonParseException;
import com.news.app.data.model.ArticleDto;
import com.news.app.data.model.ArticlesDto;
import com.news.app.data.remote.NewsService;
import com.news.app.domain.model.Articles;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;

public class NewsRepoImplTest {


    @Mock
    private NewsService newsService;

    private NewsRepoImpl newsRepoImpl;

    ArticlesDto articlesDto;

    @Before
    public void setUp() throws ParseException {
        MockitoAnnotations.initMocks(this);
        newsRepoImpl = new NewsRepoImpl(newsService);
        articlesDto = createArticleDto("2024-01-10T22:41:25Z", "2024-01-10T22:42:25Z");
    }

    @Test
    public void testGetNews() {

        when(newsService.getNews()).thenReturn(Observable.just(articlesDto));

        TestObserver<Articles> testObserver = newsRepoImpl.getNews().test();

        articlesDto.sortArticles();
        Articles expected = articlesDto.mapToArticles();

        testObserver.assertNoErrors();
        testObserver.assertValue(result -> result.articles.equals(expected.articles));
    }

    @Test
    public void testFilterNews() {

        when(newsService.getNews()).thenReturn(Observable.just(articlesDto));

        newsRepoImpl.getNews().subscribe(); // to fill the cache

        Articles result = newsRepoImpl.filterNews("SEC approves");

        Assert.assertEquals(1, result.articles.size());
        Assert.assertEquals("SEC approves bitcoin ETFs (for real this time)", result.articles.get(0).title);
    }

    @Test
    public void testGetNewsEmpty() {
        when(newsService.getNews()).thenReturn(Observable.just(new ArticlesDto(Collections.emptyList())));

        TestObserver<Articles> testObserver = newsRepoImpl.getNews().test();

        testObserver.assertNoErrors();
        testObserver.assertValue(articles -> articles.articles.isEmpty());
    }

    @Test
    public void testGetNewsError() {
        when(newsService.getNews()).thenReturn(Observable.error(new JsonParseException("json parsing failed")));

        TestObserver<Articles> testObserver = newsRepoImpl.getNews().test();

        testObserver.assertError(JsonParseException.class);
    }

    @Test
    public void testFilterNewsMatchNone() {

        when(newsService.getNews()).thenReturn(Observable.just(articlesDto));

        newsRepoImpl.getNews().subscribe(); // to fill the cache

        Articles result = newsRepoImpl.filterNews("No match");

        Assert.assertTrue(result.articles.isEmpty());
    }

    @Test
    public void testFilterNewsEmptyQuery() {

        when(newsService.getNews()).thenReturn(Observable.just(articlesDto));

        newsRepoImpl.getNews().subscribe(); // to fill the cache

        Articles result = newsRepoImpl.filterNews("");

        Assert.assertEquals(2, result.articles.size());
    }

    @Test
    public void testFilterNewsNoCache() {
        Articles result = newsRepoImpl.filterNews("SEC approves");

        Assert.assertNull(result);
    }

    private ArticlesDto createArticleDto(String date1, String date2) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        ArticleDto articleDto1 = new ArticleDto( dateFormat.parse(date1), "SEC approves bitcoin ETFs (for real this time)",
                "https://www.engadget.com/sec-approves-bitcoin-etfs-for-real-this-time-224125584.html",
                "https://www.engadget.com/sec-approves-bitcoin-etfs-for-real-this-time-224125584.html");

        ArticleDto articleDto2 = new ArticleDto(dateFormat.parse(date2), "Why Crypto Idealogues Won’t Touch Bitcoin ETFs",
                "https://www.engadget.com/sec-approves-bitcoin-etfs-for-real-this-time-224125584.html",
                "https://www.engadget.com/sec-approves-bitcoin-etfs-for-real-this-time-224125584.html");

        return new ArticlesDto(Arrays.asList(articleDto1, articleDto2));
    }

}