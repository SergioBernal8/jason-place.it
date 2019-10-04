package com.example.json.place.it.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.json.place.it.data.base.response.DataResponse
import com.example.json.place.it.data.repository.CommentsRepository
import com.example.json.place.it.domain.model.Comment

class PostDetailViewModel(private val postId: Int) : ViewModel() {
    private var data: MutableLiveData<DataResponse<Comment>>? = null
    private var repository: CommentsRepository? = null

    val newsRepository: LiveData<DataResponse<Comment>>?
        get() = data

    fun create() {
        repository = CommentsRepository.getInstance()
        data = repository!!.getCommentsForPost(postId)
    }
}
