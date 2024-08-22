package com.example.lotus.data.repository

import android.content.Context
import com.example.lotus.data.model.ApiResponse
import com.example.lotus.data.model.PostDetailResponse
import com.example.lotus.data.model.PostListResponse
import com.example.lotus.data.network.ApiService
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.ui.adapter.dataItem.ItemPost
import com.example.lotus.ui.adapter.dataItem.toPost
import com.example.lotus.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostRepository(context: Context): PostRepositoryImpl {

    private val apiService: ApiService = RetrofitClient.getRetrofitClient(context)

    override suspend fun addPost(
        content: RequestBody,
        image: MultipartBody.Part?
    ): Flow<Resource<PostDetailResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPosts(): Flow<Resource<List<ItemPost>>> {
        return flow {
            emit(Resource.Loading()) // Emit loading state
            try {
                val response = apiService.getPosts()
                when (response) {
                    is ApiResponse.Success<*> -> {
                        val posts = response.data as? List<PostListResponse> // Cast dữ liệu về List<Post>
                        if (posts.isNullOrEmpty()) {
                            emit(Resource.Error("No posts available"))
                        } else {
                            val itemPosts = posts.map { it.toPost() } // Chuyển đổi dữ liệu PostDetailResponse sang ItemPost
                            emit(Resource.Success(itemPosts))
                        }
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error("API Error: ${response.message}"))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Error("No data found"))
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error("Exception: ${e.message}"))
            }
        }
    }

    override suspend fun likePost(idPost: String): Flow<Resource<List<String>>> {
        return flow {
            try {
                val response = apiService.likePost(idPost)
                when{
                    response is ApiResponse.Success<*> -> {
                        val post = response.data as? PostDetailResponse
                        if (post == null) {
                            emit(Resource.Error("No post available"))
                        } else {
                            val  likes = post.likes
                            if (likes.isEmpty()) {
                                emit(Resource.Success(emptyList())) // Danh sách likes là rỗng
                            } else {
                                emit(Resource.Success(likes))
                            }
                        }
                    }
                }
            }
            catch (e: Exception) {
                emit(Resource.Error("Exception: ${e.message}"))
            }
        }
    }


}