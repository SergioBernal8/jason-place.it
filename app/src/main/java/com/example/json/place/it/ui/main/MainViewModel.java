package com.example.json.place.it.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.json.place.it.data.base.response.DataResponse;
import com.example.json.place.it.data.repository.PostRepository;

import com.example.json.place.it.domain.model.Post;

public class MainViewModel extends ViewModel {


    private int postId;
    private MutableLiveData<DataResponse<Post>> data;

    void start() {
        PostRepository repository = PostRepository.getInstance();
        data = repository.getAllPosts();
    }

    public LiveData<DataResponse<Post>> getData() {
        return data;
    }
}
