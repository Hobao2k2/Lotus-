package com.example.lotus.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotus.data.repository.PostRepository
import com.example.lotus.ui.adapter.dataItem.ItemPost
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val postRepository: PostRepository): ViewModel() {
    private val _posts = MutableStateFlow<Resource<List<ItemPost>>>(Resource.Loading())
    val posts: StateFlow<Resource<List<ItemPost>>> = _posts

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
    private val _likePost = MutableStateFlow<Resource<ItemPost>>(Resource.Loading())
    val likePost: StateFlow<Resource<ItemPost>> = _likePost

    fun likePost(idPost: String) {
        
        viewModelScope.launch {
            postRepository.likePost(idPost).collect {
                // Xử lý response khi like bài viết
                _likePost.value = it
            }
        }
    }




}