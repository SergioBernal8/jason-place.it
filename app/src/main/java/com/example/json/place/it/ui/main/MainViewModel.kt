package com.example.json.place.it.ui.main

import android.util.Log
import androidx.lifecycle.*

import com.example.json.place.it.data.base.response.DataResponse
import com.example.json.place.it.data.base.response.ServerResponse
import com.example.json.place.it.data.repository.PostRepository
import com.example.json.place.it.domain.manager.RealmManager
import com.example.json.place.it.domain.model.Post
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class MainViewModel : ViewModel() {

    private val posts = MutableLiveData<Post>()
    private var allData = MutableLiveData<DataResponse<List<Post>>>()

    private var manager: RealmManager? = null

    val data: LiveData<Post>
        get() = posts

    fun loadPosts() {
        val repository = PostRepository.getInstance()
        allData = repository.allPosts

        allData.observeForever { listDataResponse ->
            if (listDataResponse.response == ServerResponse.CASE_SUCCESS) {
                for (post in listDataResponse.data) {
                    savePostLocal(post)
                    posts.value = post
                }
                getAllPostFromLocal()
            } else {
                // handle error
            }
        }
    }

    fun init() {
        manager = RealmManager()
    }

    private fun savePostLocal(post: Post) {
        viewModelScope.async {
            manager!!.addPost(post)
        }
    }

    private fun getAllPostFromLocal() {
        val all = manager!!.allPost
        Log.e("TAG", "list size: " + all.size)
    }

    fun getAllData(): LiveData<DataResponse<List<Post>>> {
        return allData
    }

}
