package com.example.json.place.it.ui.post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.json.place.it.data.base.response.DataResponse;
import com.example.json.place.it.data.repository.CommentsRepository;
import com.example.json.place.it.domain.model.Comment;

public class PostDetailViewModel extends ViewModel {


    private int postId;
    private MutableLiveData<DataResponse<Comment>> data;
    private CommentsRepository repository;

    public PostDetailViewModel(int postId) {
        this.postId = postId;
    }

    public void create() {
        repository = CommentsRepository.getInstance();
        data = repository.getCommentsForPost(postId);
    }

    public LiveData<DataResponse<Comment>> getNewsRepository() {
        return data;
    }
}
