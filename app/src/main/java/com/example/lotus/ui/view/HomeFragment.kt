package com.example.lotus.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotus.R
import com.example.lotus.data.repository.PostRepository
import com.example.lotus.databinding.FragmentHomeBinding
import com.example.lotus.ui.adapter.PostAdapter
import com.example.lotus.ui.adapter.dataItem.ItemPost
import com.example.lotus.ui.adapter.dataItem.ListItem
import com.example.lotus.ui.viewModel.PostViewModel
import com.example.lotus.ui.viewModel.PostViewModelFactory
import com.example.lotus.utils.Resource
import com.example.lotus.utils.SharedPrefManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), PostAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var adapter: PostAdapter

    private val TAG = "HomeFragment"

    private val postViewModel: PostViewModel by viewModels() {
        PostViewModelFactory(PostRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        sharedPrefManager = SharedPrefManager(requireContext())

        adapter = PostAdapter(mutableListOf(), this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        getData()

        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            sharedPrefManager.clearLoginState()
        }

        binding.imgSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }



        return binding.root
    }

    private fun getData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postViewModel.fetchPosts()
                postViewModel.posts.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            Log.d(TAG, "getData: Loading")
                            binding.recyclerView.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
                            delay(300)
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            resource.data?.let { posts ->
                                val itemPosts = posts.map { post ->
                                    ItemPost(
                                        id = post.id,
                                        content = post.content,
                                        userId = post.userId,
                                        name = post.name,
                                        imageAvatar = post.imageAvatar,
                                        imagePost = post.imagePost,
                                        likes = post.likes,
                                        comments = post.comments
                                    )
                                }
                                adapter.submitList(itemPosts)
                            }
                        }
                        is Resource.Error -> {
                            Log.d(TAG, "getData: ${resource.message}")
                        }
                    }
                }
            }
        }
    }

    override fun onLikeClick(position: Int) {
        val currentList = adapter.differ.currentList.toMutableList()
        val item = currentList[position]
        val idPost = item.id

        // Cập nhật giao diện tạm thời trước khi phản hồi từ server
        updateLike(idPost, true)

        viewLifecycleOwner.lifecycleScope.launch {
            postViewModel.likePost(idPost)
            postViewModel.likePost.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.d(TAG, "onLikeClick: Loading")
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "onLikeClick: ${resource.data}")
                        // Cập nhật lại adapter nếu cần thiết
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "onLikeClick: ${resource.message}")
                    }
                }
            }
        }
    }


    private fun updateLike(idPost: String, isLiked: Boolean) {

    }

    fun scrollToTopAndRefresh() {
        // Cuộn lên đầu
        binding.recyclerView.smoothScrollToPosition(0)
        // Load lại dữ liệu
        getData()
    }


    override fun onCommentClick(position: Int) {
        TODO("Not yet implemented")
    }


}