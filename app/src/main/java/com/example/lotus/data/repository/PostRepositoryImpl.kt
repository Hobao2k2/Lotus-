package com.example.lotus.data.repository

import com.example.lotus.data.model.PostDetailResponse
import com.example.lotus.ui.adapter.dataItem.ItemPost
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostRepositoryImpl {

    suspend fun addPost(content: RequestBody, image: MultipartBody.Part?): Flow<Resource<PostDetailResponse>>

    suspend fun getPosts(): Flow<Resource<List<ItemPost>>>

    suspend fun likePost(idPost: String): Flow<Resource<List<String>>>
}