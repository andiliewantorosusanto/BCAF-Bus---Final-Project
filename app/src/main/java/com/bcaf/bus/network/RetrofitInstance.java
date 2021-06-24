package com.bcaf.bus.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(final String sToken) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //TimeOut
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer "+sToken) // <-- this is the important line
                    .header("Content-Type", "application/json");

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        OkHttpClient client = httpClient.build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConfigDatabase.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;

    }

    //Reset Retrofit
    public static Retrofit clearRetrofitInstance() {
        retrofit = null;
        return retrofit;
    }

}