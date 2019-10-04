package com.example.json.place.it.data.repository;


import androidx.lifecycle.MutableLiveData;

import com.example.json.place.it.data.base.BaseRepository;
import com.example.json.place.it.data.base.api.PostAPI;
import com.example.json.place.it.data.base.response.DataResponse;
import com.example.json.place.it.data.base.response.ServerResponse;
import com.example.json.place.it.domain.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostRepository {

    private static PostRepository instance;

    private PostAPI api;

    public static PostRepository getInstance() {
        if (instance == null) {
            instance = new PostRepository();
        }
        return instance;
    }

    private PostRepository() {
        api = BaseRepository.createInstance(PostAPI.class);
    }

    public MutableLiveData<DataResponse<List<Post>>> getAllPosts() {
        final MutableLiveData<DataResponse<List<Post>>> data = new MutableLiveData<>();

        api.getAllPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(new DataResponse<>(response.body(), ServerResponse.CASE_SUCCESS));
                } else
                    data.setValue(new DataResponse<List<Post>>(null, ServerResponse.CASE_EMPTY_RESPONSE));
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                data.setValue(new DataResponse<List<Post>>(null, ServerResponse.CASE_EMPTY_RESPONSE));
            }
        });


        return data;
    }
}
