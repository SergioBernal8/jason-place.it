package com.example.json.place.it.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.json.place.it.R;
import com.example.json.place.it.domain.model.realm.LocalPost;
import com.example.json.place.it.ui.custom.SwipeAndDeleteCallback;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private MainViewModel model;
    private PostAdapter mAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        RecyclerView recyclerView = findViewById(R.id.recycle_view_posts);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PostAdapter();

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

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        ImageButton toolbarButton = toolbar.findViewById(R.id.toolbarImageButton);
        toolbar.setTitle(R.string.post);
        TabLayout tabLayout = findViewById(R.id.tab_layout_main);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    model.filterData(Filter.ALL);
                } else {
                    model.filterData(Filter.FAVORITES);
                }
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
            progressBar.setVisibility(View.VISIBLE);
            model.clearAllLocalData();
        });

        recyclerView.setAdapter(mAdapter);

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.init();
        model.getAllPostFromLocal();
        model.getLocalPosts().observe(this, localPosts -> {
            mAdapter.setData(localPosts);
            progressBar.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.getRemotePosts().removeObservers(this);
    }
}
