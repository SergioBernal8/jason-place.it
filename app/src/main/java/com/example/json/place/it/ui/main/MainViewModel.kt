package com.example.json.place.it.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.json.place.it.data.base.response.DataResponse
import com.example.json.place.it.data.base.response.ServerResponse
import com.example.json.place.it.data.repository.PostRepository
import com.example.json.place.it.domain.manager.RealmManager
import com.example.json.place.it.domain.model.Post
import com.example.json.place.it.domain.model.realm.LocalPost
import kotlinx.coroutines.async


enum class Filter {
    ALL, FAVORITES
}

internal class MainViewModel : ViewModel() {

    private val allLocalPosts = MutableLiveData<List<LocalPost>>()
    private val filteredPosts = MutableLiveData<List<LocalPost>>()
    private var allRemotePosts = MutableLiveData<DataResponse<List<Post>>>()

    private var manager: RealmManager? = null

    val localPosts: LiveData<List<LocalPost>>
        get() = filteredPosts

    val remotePosts: LiveData<DataResponse<List<Post>>>
        get() = allRemotePosts

    private fun loadPosts() {
        val repository = PostRepository.getInstance()
        allRemotePosts = repository.allPosts

        allRemotePosts.observeForever { listDataResponse ->
            if (listDataResponse.response == ServerResponse.CASE_SUCCESS) {
                for (i in listDataResponse.data.indices) {
                    savePostLocal(listDataResponse.data[i], i >= 20)
                }
                getAllPostFromLocal()
            }
        }
    }

    fun init() {
        manager = RealmManager()
    }

    fun filterData(filter: Filter) {
        if (filter == Filter.ALL) {
            filteredPosts.value = allLocalPosts.value
        } else {
            filteredPosts.value = allLocalPosts.value?.filter { it.isFavorite }?.toList()
        }
    }

    private fun savePostLocal(post: Post, withBlueDot: Boolean = false) {
        viewModelScope.async {
            manager?.addPost(post, withBlueDot)
        }
    }

    fun getAllPostFromLocal() {
        val all = manager!!.allPost

        if (all.isEmpty())
            loadPosts()
        else {
            allLocalPosts.value = all
            filteredPosts.value = all
        }
    }

    fun clearAllLocalData() {
        viewModelScope.async {
            manager?.clearAllLocalPost()
            loadPosts()
        }
    }

    fun deletePostWithId(id: Long) {
        viewModelScope.async {
            manager?.deletePost(id)
        }
    }

}
