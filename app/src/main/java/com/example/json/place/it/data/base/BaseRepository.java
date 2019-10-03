package com.example.json.place.it.data.base;

import com.example.json.place.it.data.base.api.APIConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRepository {

    private static Retrofit instance = new Retrofit.Builder()
            .baseUrl(APIConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static <T> T createInstance(Class<T> apiClass) {
        return instance.create(apiClass);
    }
}
