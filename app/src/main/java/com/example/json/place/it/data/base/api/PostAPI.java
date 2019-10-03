package com.example.json.place.it.data.base.api;


import com.example.json.place.it.domain.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostAPI {

    @GET(APIConstants.PATH_POST)
    Call<List<Post>> getAllPosts();
}
