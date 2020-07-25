package com.example.creativeminds_second_task.data.remote;


import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.creativeminds_second_task.Constants.BASE_URL;

public class RetrofitClientInstance {
    private static Retrofit retrofit;


    public static Retrofit getRetrofitInstance(Context context) {

        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS);
            httpClient.readTimeout(240, TimeUnit.SECONDS);
            httpClient.connectTimeout(240, TimeUnit.SECONDS);
            httpClient.writeTimeout(240, TimeUnit.SECONDS);
// add your other interceptors …

// add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the imp

            retrofit = new Retrofit.Builder()

                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }


}
