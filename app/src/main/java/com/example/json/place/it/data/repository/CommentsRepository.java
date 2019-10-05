package com.example.json.place.it.data.repository;


import androidx.lifecycle.MutableLiveData;

import com.example.json.place.it.data.base.BaseRepository;
import com.example.json.place.it.data.base.api.CommentsAPI;
import com.example.json.place.it.data.base.response.DataResponse;
import com.example.json.place.it.data.base.response.ServerResponse;
import com.example.json.place.it.domain.model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsRepository {
    private static CommentsRepository instance;

    private CommentsAPI api;

    public static CommentsRepository getInstance() {
        if (instance == null) {
            instance = new CommentsRepository();
        }
        return instance;
    }

    private CommentsRepository() {
        api = BaseRepository.createInstance(CommentsAPI.class);
    }

    public MutableLiveData<DataResponse<List<Comment>>> getCommentsForPost(final long postId) {
        final MutableLiveData<DataResponse<List<Comment>>> data = new MutableLiveData<>();

        api.getCommentsForPost(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(new DataResponse<>(response.body(), ServerResponse.CASE_SUCCESS));
                } else
                    data.setValue(new DataResponse<>(null, ServerResponse.CASE_EMPTY_RESPONSE));
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                data.setValue(new DataResponse<>(null, ServerResponse.CASE_ERROR));
            }
        });


        return data;
    }
}
