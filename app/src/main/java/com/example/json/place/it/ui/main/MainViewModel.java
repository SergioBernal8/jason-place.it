package com.example.json.place.it.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.json.place.it.data.base.response.DataResponse;
import com.example.json.place.it.data.base.response.ServerResponse;
import com.example.json.place.it.data.repository.PostRepository;
import com.example.json.place.it.domain.model.Post;
import com.example.json.place.it.domain.manager.RealmManager;

import java.util.List;

class MainViewModel extends ViewModel {

    private MutableLiveData<Post> posts = new MutableLiveData<>();
    private MutableLiveData<DataResponse<List<Post>>> allData = new MutableLiveData<>();

    private RealmManager manager;

    void loadPosts() {
        final PostRepository repository = PostRepository.getInstance();
        allData = repository.getAllPosts();

        allData.observeForever(new Observer<DataResponse<List<Post>>>() {
            @Override
            public void onChanged(DataResponse<List<Post>> listDataResponse) {
                if (listDataResponse.response == ServerResponse.CASE_SUCCESS) {
                    for (Post post : listDataResponse.data) {
                        savePostLocal(post);
                        posts.setValue(post);
                    }
                    getAllPostFromLocal();
                } else {
                    // handle error
                }

            }
        });
    }

    void init() {
        manager = new RealmManager();
    }

    private void savePostLocal(Post post) {
        manager.addPost(post);
    }

    private void getAllPostFromLocal() {
        final List<Post> all = manager.getAllPost();
        Log.e("TAG", "list size: " + all.size());
    }

    LiveData<Post> getData() {
        return posts;
    }

    LiveData<DataResponse<List<Post>>> getAllData() {
        return allData;
    }

}
