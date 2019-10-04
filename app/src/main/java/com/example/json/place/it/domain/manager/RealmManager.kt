package com.example.json.place.it.domain.manager


import android.util.Log

import com.example.json.place.it.domain.model.Post
import com.example.json.place.it.domain.model.realm.LocalPost

import io.realm.Realm
import kotlinx.coroutines.coroutineScope

class RealmManager {
    private val TAG = "RealmManager"

    val allPost: List<Post>
        get() {
            val realm = Realm.getDefaultInstance()
            val results = realm.where(LocalPost::class.java).findAll()

            val data = realm.copyFromRealm(results)

            return data.map { Post(it) }.toList()
        }

    suspend fun addPost(post: Post) {
        val localPost = LocalPost()
        localPost.userId = post.userId!!.toLong()
        localPost.id = post.id!!.toLong()
        localPost.title = post.title
        localPost.body = post.body

        val realm = Realm.getDefaultInstance()

        coroutineScope {
            realm.beginTransaction()
            realm.insertOrUpdate(localPost)
            realm.commitTransaction()
        }

        /*realm.executeTransactionAsync { r ->
            r.insertOrUpdate(localPost)
            Log.i(TAG, "task finished")
        }*/
    }
}
