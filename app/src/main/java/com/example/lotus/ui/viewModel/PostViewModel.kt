package com.example.lotus.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotus.data.model.Post
import com.example.lotus.data.repository.PostRepository
import com.example.lotus.ui.adapter.dataItem.ItemPost
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val postRepository: PostRepository): ViewModel() {
    private val _posts = MutableStateFlow<Resource<List<Post>>>(Resource.Loading())
    val posts: StateFlow<Resource<List<Post>>> = _posts

    init {
        fetchPosts()
    }

    // Hàm để lấy danh sách bài viết
    fun fetchPosts() {
        viewModelScope.launch {
            postRepository.getPosts().collect { resource ->
                _posts.value = resource
                Log.d("abc", "fetchPosts: resource = ${resource.data}")
            }
        }
    }

    // Hàm để thêm bài viết mới (nếu cần)
//    fun addPost(content: String, image: MultipartBody.Part?) {
//        viewModelScope.launch {
//            postRepository.addPost(content, image).collect { resource ->
//                // Xử lý response khi thêm bài viết nếu cần
//            }
//        }
//    }
    private val _likePost = MutableStateFlow<Resource<Post>>(Resource.Loading())
    val likePost: StateFlow<Resource<Post>> = _likePost

    fun likePost(idPost: String) {
        
        viewModelScope.launch {
            postRepository.likePost(idPost).collect {
                // Xử lý response khi like bài viết
//                Log.d("updateUI", "likePost: ${it.data}")
                _likePost.value = it
            }
        }
    }

    private val _deletePost = MutableStateFlow<Resource<Post>>(Resource.Loading())
    val deletePost: StateFlow<Resource<Post>> = _deletePost

    fun deletePost(idPost: String) {
        viewModelScope.launch {
            postRepository.deletePost(idPost).collect {
                // Xử lý response khi xóa bài viết
                _deletePost.value = it
            }
        }
    }

//    private val _updatePost = MutableStateFlow<Resource<Post>>(Resource.Loading())
//    val updatePost: StateFlow<Resource<Post>> = _updatePost
//
//    fun updatePost(idPost: String, content: String) {
//        viewModelScope.launch {
//            postRepository.updatePost(idPost, content).collect {
//                // Xử lý response khi cập nhật bài viết
//                _updatePost.value = it
//            }
//        }
//    }

    private val _postById = MutableStateFlow<Resource<Post>>(Resource.Loading())
    val postById: StateFlow<Resource<Post>> = _postById

    fun getPostById(idPost: String) {
        viewModelScope.launch {
            postRepository.getPostById(idPost).collect {
                // Xử lý response khi lấy bài viết theo id
                _postById.value = it
            }
        }
    }

}