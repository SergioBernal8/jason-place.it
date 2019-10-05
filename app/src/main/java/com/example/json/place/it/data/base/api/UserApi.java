package com.example.json.place.it.data.base.api;

import com.example.json.place.it.domain.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {

    @GET(APIConstants.PATH_USERS)
    Call<List<User>> getUser(@Query("id") int id);
}
