package com.example.lotus.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotus.R
import com.example.lotus.data.model.Comment
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
    private lateinit var userId: String

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
        userId = sharedPrefManager.getUserId() ?: ""

        adapter = PostAdapter( userId,this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val window: Window = requireActivity().window
        window.statusBarColor = resources.getColor(R.color.white)

        getData()

        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            sharedPrefManager.clearLoginState()
        }

        binding.avatarProfile

        binding.addPostTv.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.fragment_container, AddPostFragment())
                .addToBackStack(null)
                .commit()

            requireActivity().findViewById<FrameLayout>(R.id.fragment_container).visibility = View.VISIBLE
        }

        binding.imgSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun getData() {

        postViewModel.fetchPosts()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                            Log.d(TAG, "getData: ${resource.data}")
                            adapter.submitList(resource.data)
                        }
                        is Resource.Error -> {
                            Log.d(TAG, "getData: ${resource.message}")
                        }
                    }
                }
            }
        }
    }

    override fun onItemClick(position: Int) {
        val selectedPost = adapter.currentList[position]

        val bundle = Bundle().apply {
            putString("postId", selectedPost.id)
        }

        val detailFragment = PostDetailFragment().apply {
            arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .add(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()


        requireActivity().findViewById<FrameLayout>(R.id.fragment_container).visibility = View.VISIBLE
    }

    override fun onLikeClick(position: Int) {
        val item = adapter.currentList[position]
        val isCurrentlyLiked = item.likes.contains(userId)
        val updatedLikes = if (isCurrentlyLiked) {
            item.likes.filter { it != userId }
        } else {
            item.likes + userId
        }

        updateUI(position, updatedLikes)

        postViewModel.likePost(item.id)

        viewLifecycleOwner.lifecycleScope.launch {
            postViewModel.likePost.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.d(TAG, "onLikeClick: Loading")
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "onLikeClick: ${resource.data}")
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "onLikeClick: ${resource.message}")
                    }
                }
            }


        }

    }

    private fun updateUI(position: Int, updatedLikes: List<String>) {
        val currentList = adapter.currentList.toMutableList()
        val item = currentList[position].copy(likes = updatedLikes)
        currentList[position] = item
        adapter.submitList(currentList)
    }

    fun scrollToTopAndRefresh() {

        binding.recyclerView.smoothScrollToPosition(0)

        getData()
    }


    override fun onCommentClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onEditPostClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDeletePostClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onSharePostClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onSavePostClick(position: Int) {
        TODO("Not yet implemented")
    }


}