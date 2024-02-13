package com.news.app.data.remote;

import androidx.annotation.NonNull;

import com.news.app.data.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockInterCeptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String uri = chain.request().url().uri().toString();
        String responseString = Constants.NEWS_JSON;
        if(uri.contains(Constants.BASE_URL+"news")) {
            return chain.proceed(chain.request())
                    .newBuilder()
                    .code(200)
                    .protocol(Protocol.HTTP_2)
                    .message(responseString)
                    .body(
                            ResponseBody.create(
                                    MediaType.parse("application/json"),
                                    responseString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        } else {
            return chain.proceed(chain.request())
                    .newBuilder()
                    .code(404)
                    .protocol(Protocol.HTTP_2)
                    .message("")
                    .body(ResponseBody.create(
                                    MediaType.parse("application/json"),
                                    responseString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        }

    }
}
