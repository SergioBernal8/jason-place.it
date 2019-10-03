package com.example.json.place.it.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.json.place.it.R;
import com.example.json.place.it.data.base.response.DataResponse;
import com.example.json.place.it.data.base.response.ServerResponse;
import com.example.json.place.it.domain.model.Post;

public class MainActivity extends AppCompatActivity {

    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.start();
        model.getData().observe(this, new Observer<DataResponse<Post>>() {
            @Override
            public void onChanged(DataResponse<Post> responseData) {
                if (responseData.response == ServerResponse.CASE_SUCCESS) {

                } else {

                }
            }
        });
    }
}
