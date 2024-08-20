package com.example.lotus.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotus.data.model.FriendId
import com.example.lotus.data.model.IdRequest
import com.example.lotus.data.model.Post
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.RegisterResponse
import com.example.lotus.data.model.User
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.ui.adapter.dataItem.Item2

import kotlinx.coroutines.Job
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
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


    fun getUserProfile(): Job {
        return viewModelScope.launch {
            val data = userRepository.getUserProfile()
                _userProfile.value = data
        }
    }

    fun addPost(content: String, user: String, imageFile: File?): Job {
        return viewModelScope.launch {
            try {
                val data = userRepository.addPost(content, user, imageFile)
                _post.value = data
            } catch (e: Exception) {
                e.printStackTrace()
                // Xử lý lỗi nếu cần
                _post.value = null // Hoặc có thể gán một giá trị mặc định/ lỗi nào đó
            }
        }
    }

    fun getAllPost():Job {
        return viewModelScope.launch {
            try {
                // Giả sử userRepository.getAllPost() trả về List<Post>
                _postAll.value = userRepository.gelAllPost()
                Log.i("test", "du lieu ok")
            } catch (e: Exception) {
                // Xử lý lỗi nếu cần
                // Có thể cập nhật _postAll với dữ liệu rỗng hoặc hiển thị thông báo lỗi
                _postAll.value = emptyList()
                Log.e("test", "du lieu rong", e)
            }
        }
    }
//    fun getAllUser(){
//        viewModelScope.launch {
//            try {
//               _userAll.value=userRepository.gelAllUsers()
//            } catch (e: Exception) {
//                _userAll.value = emptyList()
//                Log.e("ViewModel", "Failed to get users", e)
//            }
//        }
//    }
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

    fun addFriend(idRequest: IdRequest){
        viewModelScope.launch {
           userRepository.addFriend(idRequest)
        }
    }
    fun unFriend(friendId: FriendId){
        viewModelScope.launch {
            userRepository.unFriend(friendId)
        }
    }
}


