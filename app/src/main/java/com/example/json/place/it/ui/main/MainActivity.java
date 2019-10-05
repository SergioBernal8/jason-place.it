package com.example.json.place.it.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.json.place.it.R;
import com.example.json.place.it.domain.model.realm.LocalPost;
import com.example.json.place.it.ui.custom.SwipeAndDeleteCallback;
import com.example.json.place.it.ui.main.adapter.OnItemClickListener;
import com.example.json.place.it.ui.main.adapter.PostAdapter;
import com.example.json.place.it.ui.post.PostDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private MainViewModel model;
    private PostAdapter mAdapter;
    private ProgressBar progressBar;
    private Filter filter = Filter.ALL;
    private boolean firStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        RecyclerView recyclerView = findViewById(R.id.recycle_view_posts);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        OnItemClickListener listener = this::goToDetails;

        mAdapter = new PostAdapter(listener);

        SwipeAndDeleteCallback swipeGesture = new SwipeAndDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                super.onSwiped(viewHolder, direction);
                int position = viewHolder.getAdapterPosition();
                try {
                    final long id = model.getLocalPosts().getValue().get(position).getId();
                    model.deletePostWithId(id);
                    mAdapter.deleteItem(position);
                } catch (NullPointerException e) {
                    Log.wtf("", "error deleting item");
                }
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeGesture);

        itemTouchHelper.attachToRecyclerView(recyclerView);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        ImageButton toolbarButton = toolbar.findViewById(R.id.toolbarImageButton);
        toolbar.setTitle(R.string.post);
        TabLayout tabLayout = findViewById(R.id.tab_layout_main);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> {
            model.clearAllLocalData(false);
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    filter = Filter.ALL;
                else
                    filter = Filter.FAVORITES;

                model.filterData(filter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setSupportActionBar(toolbar);

        toolbarButton.setImageResource(R.mipmap.ic_refresh);
        toolbarButton.setOnClickListener(v -> {
            tabLayout.getTabAt(0).select();
            progressBar.setVisibility(View.VISIBLE);
            model.clearAllLocalData(true);
        });

        recyclerView.setAdapter(mAdapter);

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.init();
        model.getAllPostFromLocal(filter);
        model.getLocalPosts().observe(this, localPosts -> {
            mAdapter.setData(localPosts);
            progressBar.setVisibility(View.GONE);
            if (!firStart) firStart = true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firStart) {
            model.getAllPostFromLocal(filter);
        }
    }

    private void goToDetails(LocalPost localPost) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra("postId", localPost.getId());
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.getRemotePosts().removeObservers(this);
    }
}
