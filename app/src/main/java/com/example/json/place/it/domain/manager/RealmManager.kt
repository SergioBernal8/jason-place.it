package com.example.json.place.it.domain.manager


import android.util.Log
import com.example.json.place.it.domain.model.Post
import com.example.json.place.it.domain.model.realm.LocalPost
import io.realm.Realm
import kotlinx.coroutines.coroutineScope

class RealmManager {
    private val TAG = "RealmManager"

    val allPost: List<LocalPost>
        get() {
            val realm = Realm.getDefaultInstance()
            val results = realm.where(LocalPost::class.java).findAll()

            return realm.copyFromRealm(results)
        }


    suspend fun addPost(post: Post, withBlueDot: Boolean = true) {
        val localPost = LocalPost()
        localPost.userId = post.userId!!.toLong()
        localPost.id = post.id!!.toLong()
        localPost.title = post.title
        localPost.body = post.body
        localPost.isRead = withBlueDot

        val realm = Realm.getDefaultInstance()

        coroutineScope {
            realm.beginTransaction()
            realm.insertOrUpdate(localPost)
            realm.commitTransaction()
        }
    }

    suspend fun clearAllLocalPost() {
        val realm = Realm.getDefaultInstance()

        coroutineScope {
            realm.beginTransaction()
            realm.delete(LocalPost::class.java)
            realm.commitTransaction()
            Log.i(TAG, "transaction finished")
        }
    }

    fun getPost(id: Long): LocalPost? {
        val realm = Realm.getDefaultInstance()

        return realm.where(LocalPost::class.java).equalTo("id", id).findFirst()
    }

    suspend fun deletePost(id: Long) {
        val realm = Realm.getDefaultInstance()

        coroutineScope {
            realm.beginTransaction()
            val obj = realm.where(LocalPost::class.java).equalTo("id", id).findFirst()
            obj?.deleteFromRealm()
            realm.commitTransaction()
            Log.i(TAG, "transaction finished")
        }
    }

    suspend fun markPostFavorite(id: Long, favorite: Boolean) {
        val realm = Realm.getDefaultInstance()

        coroutineScope {
            realm.beginTransaction()
            val obj = realm.where(LocalPost::class.java).equalTo("id", id).findFirst()
            obj?.isFavorite = favorite
            realm.commitTransaction()
            Log.i(TAG, "transaction finished")
        }
    }

    suspend fun markPostRead(id: Long) {
        val realm = Realm.getDefaultInstance()

        coroutineScope {
            realm.beginTransaction()
            val obj = realm.where(LocalPost::class.java).equalTo("id", id).findFirst()
            obj?.isRead = true
            realm.commitTransaction()
            Log.i(TAG, "transaction finished")
        }
    }
}
