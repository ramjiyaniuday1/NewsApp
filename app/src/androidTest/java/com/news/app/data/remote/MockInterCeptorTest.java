package com.news.app.data.remote;

import static org.junit.Assert.assertEquals;

import com.news.app.data.Constants;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MockInterCeptorTest {

    private OkHttpClient client;

    @Before
    public void setUp() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new MockInterCeptor())
                .build();
    }

    @Test
    public void testIntercept_newsApi() throws IOException {
        Request request = new Request.Builder()
                .url(Constants.BASE_URL+"news")
                .build();

        Response response = client.newCall(request).execute();

        assertEquals(200, response.code());
        assertEquals(Constants.NEWS_JSON, response.body().string());
    }

    @Test
    public void testIntercept_otherApi() throws IOException {
        Request request = new Request.Builder()
                .url(Constants.BASE_URL+"ddfdsgata")
                .build();

        Response response = client.newCall(request).execute();

        assertEquals(404, response.code());
      //  assertEquals("", response.body().string());
    }
}

