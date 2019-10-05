package com.example.json.place.it.ui.post;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.json.place.it.R;
import com.example.json.place.it.data.base.response.ServerResponse;
import com.example.json.place.it.ui.post.adapter.CommentAdapter;

public class PostDetailActivity extends AppCompatActivity {

    private PostDetailViewModel model;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);


        RecyclerView recyclerView = findViewById(R.id.recycle_view_comments);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CommentAdapter();
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        ImageButton toolbarButton = toolbar.findViewById(R.id.toolbarImageButton);
        ImageButton toolbarButtonBack = toolbar.findViewById(R.id.toolbarImageButtonBack);
        toolbar.setTitle("");

        toolbarButtonBack.setOnClickListener(v -> finish());
        toolbarButtonBack.setVisibility(View.VISIBLE);

        toolbarButton.setOnClickListener(v -> model.updatePostFavorite());

        model = ViewModelProviders.of(this).get(PostDetailViewModel.class);
        model.init();
        final long postId = getIntent().getLongExtra("postId", 0);
        model.getPost(postId);

        model.isFavorite().observe(this, isFavorite -> {
            if (toolbarButton.getVisibility() == View.INVISIBLE)
                toolbarButton.setVisibility(View.VISIBLE);
            if (isFavorite) toolbarButton.setImageResource(R.mipmap.favorite_star);
            else toolbarButton.setImageResource(R.mipmap.favorite_star_outline);
        });

        model.getLocalPost().observe(this, localPost -> {
            TextView textView = findViewById(R.id.text_view_description);
            textView.setText(localPost.getBody());
        });

        model.getUser().observe(this, user -> {
            TextView textViewName = findViewById(R.id.textViewUserName);
            TextView textViewEmail = findViewById(R.id.textViewEmail);
            TextView textViewPhone = findViewById(R.id.textViewPhone);
            TextView textViewWeb = findViewById(R.id.textViewWebSite);
            textViewName.setText(user.getName());
            textViewEmail.setText(user.getEmail());
            textViewPhone.setText(user.getPhone());
            textViewWeb.setText(user.getWebsite());
        });

        model.getComments().observe(this, commentDataResponse -> {
            if (commentDataResponse.response == ServerResponse.CASE_SUCCESS)
                adapter.setData(commentDataResponse.data);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.getUserResponse().removeObservers(this);
    }
}
