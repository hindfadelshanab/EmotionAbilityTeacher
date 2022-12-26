package com.nuwa.robot.r2022.emotionalability.api;

import static com.nuwa.robot.r2022.emotionalability.utils.Constants.BASE_URL;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {




    private Context context;
    private InterfaceApi apiInterface;

    public ClientApi(Context context) {
        this.context = context;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttp = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttp)
                .addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(InterfaceApi.class);
    }

    public InterfaceApi getApiInterface() {
        return apiInterface;
    }


}
