package com.example.lotus.data.repository

import android.content.Context
import android.util.Log
import com.example.lotus.data.model.ApiResponse
import com.example.lotus.data.model.PostUserId
import com.example.lotus.data.model.PostUser
import com.example.lotus.data.network.ApiService
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.ui.adapter.dataItem.ItemPost
import com.example.lotus.ui.adapter.dataItem.toPost
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.math.log

class PostRepository(context: Context): PostRepositoryImpl {

    private val apiService: ApiService = RetrofitClient.getRetrofitClient(context)

    override suspend fun addPost(
        content: RequestBody,
        image: MultipartBody.Part?
    ): Flow<Resource<PostUserId>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPosts(): Flow<Resource<List<ItemPost>>> {
        return flow {
            emit(Resource.Loading()) // Emit loading state
            try {
                val response = apiService.getPosts()
                val itemPosts = response.map { postUser -> postUser.toPost() }
                emit(Resource.Success(itemPosts))
            } catch (e: Exception) {
                emit(Resource.Error("Exception: ${e.message}"))
            }
        }
    }

    override suspend fun likePost(idPost: String): Flow<Resource<ItemPost>> {
        return flow {
            try {
                val response = apiService.likePost(idPost).toPost()
                emit(Resource.Success(response))
            }
            catch (e: Exception) {
                emit(Resource.Error("Exception: ${e.message}"))
            }
        }
    }


}