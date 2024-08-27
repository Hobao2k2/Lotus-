package com.example.lotus.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotus.data.model.FriendId
import com.example.lotus.data.model.Post
import com.example.lotus.data.model.ReceiverId
import com.example.lotus.data.model.User
import com.example.lotus.data.repository.UserRepository

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile = _userProfile.asStateFlow()

    private val _post= MutableStateFlow<Post?>(null)
    val post=_post.asStateFlow()

    private val _postAll= MutableStateFlow<List<Post>?>(null)
    val postAll=_postAll.asStateFlow()

    private val _userAll=MutableStateFlow<List<User>?>(null)
    val userAll=_userAll.asStateFlow()

    private val _userId = MutableStateFlow<User?>(null)
    val userId = _userId.asStateFlow()

    private val _like= MutableStateFlow<Int?>(null)
    val like=_like.asStateFlow()


    fun getUserProfile(): Job {
        return viewModelScope.launch {
            val data = userRepository.getUserProfile()
                _userProfile.value = data
        }
    }

    fun addPost(content: String, imageFile: File?): Job {
        return viewModelScope.launch {
            try {
                val data = userRepository.addPost(content,  imageFile)
                _post.value = data
            } catch (e: Exception) {
                e.printStackTrace()
                _post.value = null
            }
        }
    }

    fun getAllPost():Job {
        return viewModelScope.launch {
            try {
                _postAll.value = userRepository.gelAllPost()
            } catch (e: Exception) {
                _postAll.value = emptyList()
                Log.e("test", "du lieu rong", e)
            }
        }
    }
    fun  SearchUser(keyWord:String){
        viewModelScope.launch {
            try {
                _userAll.value=userRepository.searchUser(keyWord)
            } catch (e: Exception) {
                _userAll.value = emptyList()
                Log.e("ViewModel", "Failed to get users", e)
            }
        }
    }

    fun getUserById(id:String){
        viewModelScope.launch {
            try {
                _userId.value=userRepository.getUserById(id)
            }catch (e:Exception){
                Log.e("ViewModel","Not User By Id")
            }
        }
    }

    fun addFriend(idRequest: ReceiverId){
        viewModelScope.launch {
           userRepository.addFriend(idRequest)
        }
    }
    fun unFriend(friendId: FriendId){
        viewModelScope.launch {
            userRepository.unFriend(friendId)
        }
    }

    fun rejectFriend(friendId: FriendId){
        viewModelScope.launch {
            userRepository.rejectFriend(friendId)
        }
    }

    fun acceptFriend(friendId: FriendId){
        viewModelScope.launch {
            userRepository.acceptFriend(friendId)
        }
    }



    private val _postLikes = MutableStateFlow<List<String>>(emptyList())
    val postLikes = _postLikes.asStateFlow()

    fun likePost(postId: String):Job {
      return  viewModelScope.launch {
            val updatedPost = userRepository.likePost(postId)
            _postLikes.value=updatedPost.likes
        }
    }
}


