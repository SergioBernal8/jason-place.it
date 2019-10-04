package com.example.json.place.it.domain.manager;


import android.util.Log;

import com.example.json.place.it.domain.model.Post;
import com.example.json.place.it.domain.model.realm.LocalPost;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmManager {
    private final String TAG = "RealmManager";

    public void addPost(Post post) {
        final LocalPost localPost = new LocalPost();
        localPost.setUserId(post.getUserId());
        localPost.setId(post.getId());
        localPost.setTitle(post.getTitle());
        localPost.setBody(post.getBody());

        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm r) {
                r.insertOrUpdate(localPost);
                Log.i(TAG, "task finished");
            }
        });
    }

    public List<Post> getAllPost() {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<LocalPost> results = realm.where(LocalPost.class).findAll();

        List<LocalPost> data = realm.copyFromRealm(results);
        List<Post> list = new ArrayList<>();

        for (LocalPost localPost : data) {
            list.add(new Post(localPost));
        }

        return list;
    }
}
