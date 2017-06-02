package com.figengungor.rxjava2playground;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by figengungor on 6/3/2017.
 */

public class ServiceGenerator {

    public static String baseUrl = "https://api.github.com";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(new OkHttpClient().newBuilder().addInterceptor(createLoggingInterceptor()).build());

    private static Retrofit retrofit;

    private static HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }

    public static <T> T createService(Class<T> serviceClass) {
        retrofit = retrofitBuilder.baseUrl(baseUrl).build();
        return retrofit.create(serviceClass);
    }


}
