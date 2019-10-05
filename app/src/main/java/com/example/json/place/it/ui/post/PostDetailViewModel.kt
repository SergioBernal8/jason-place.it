package com.example.json.place.it.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.json.place.it.data.base.response.DataResponse
import com.example.json.place.it.data.base.response.ServerResponse
import com.example.json.place.it.data.repository.CommentsRepository
import com.example.json.place.it.data.repository.UserRepository
import com.example.json.place.it.domain.manager.RealmManager
import com.example.json.place.it.domain.model.Comment
import com.example.json.place.it.domain.model.User
import com.example.json.place.it.domain.model.realm.LocalPost
import kotlinx.coroutines.async

class PostDetailViewModel : ViewModel() {

    private var commentData = MutableLiveData<DataResponse<List<Comment>>>()
    private var userDataResponse = MutableLiveData<DataResponse<User>>()
    private var userData = MutableLiveData<User>()
    private var isFavoriteData = MutableLiveData<Boolean>()
    private var localPostData = MutableLiveData<LocalPost>()

    private var manager: RealmManager? = null

    var post: LocalPost? = null

    val comments: LiveData<DataResponse<List<Comment>>>
        get() = commentData

    val user: LiveData<User>
        get() = userData

    val userResponse: LiveData<DataResponse<User>>
        get() = userDataResponse

    val isFavorite: LiveData<Boolean>
        get() = isFavoriteData

    val localPost: LiveData<LocalPost>
        get() = localPostData


    private fun loadComments(postId: Long) {
        val repository = CommentsRepository.getInstance()
        commentData = repository.getCommentsForPost(postId)
    }

    private fun loadUser(userId: Int) {
        val repository = UserRepository.getInstance()
        userDataResponse = repository.getUser(userId)

        userDataResponse.observeForever {
            if (it.response == ServerResponse.CASE_SUCCESS) {
                userData.value = it.data
            }
        }
    }

    fun init() {
        manager = RealmManager()
    }

    fun getPost(id: Long) {
        post = manager?.getPost(id)
        if (post != null) {
            loadUser(post!!.userId.toInt())
            loadComments(post!!.id)
            updatePostRead()
            localPostData.value = post
            isFavoriteData.value = post!!.isFavorite
        }
    }

    fun updatePostFavorite() {
        if (post == null) return
        viewModelScope.async {
            isFavoriteData.value = !post!!.isFavorite
            manager?.markPostFavorite(post!!.id, !post!!.isFavorite)
        }
    }

    private fun updatePostRead() {
        if (post == null) return
        viewModelScope.async {
            manager?.markPostRead(post!!.id)
        }
    }
}
