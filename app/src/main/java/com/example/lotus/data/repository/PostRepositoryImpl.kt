package com.example.lotus.data.repository

import com.example.lotus.data.model.Post
import com.example.lotus.data.model.PostUserId
import com.example.lotus.ui.adapter.dataItem.ItemPost
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostRepositoryImpl {

    suspend fun addPost(content: RequestBody, image: MultipartBody.Part?): Flow<Resource<PostUserId>>

    suspend fun getPosts(): Flow<Resource<List<Post>>>

    suspend fun likePost(idPost: String): Flow<Resource<Post>>

    suspend fun deletePost(idPost: String): Flow<Resource<Post>>

    suspend fun getPostById(idPost: String): Flow<Resource<Post>>
}