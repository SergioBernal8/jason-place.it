package com.example.json.place.it.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.json.place.it.R;
import com.example.json.place.it.domain.model.Post;

public class MainActivity extends AppCompatActivity {

    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.init();
        model.loadPosts();
        model.getData().observe(this, new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                Log.e("TAG", post.getTitle());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.getAllData().removeObservers(this);
    }
}
