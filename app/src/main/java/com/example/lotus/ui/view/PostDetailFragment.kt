package com.example.lotus.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.data.repository.PostRepository
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.FragmentPostDetailBinding
import com.example.lotus.ui.adapter.CommentAdapter
import com.example.lotus.ui.adapter.PostAdapter
import com.example.lotus.ui.viewModel.PostViewModel
import com.example.lotus.ui.viewModel.PostViewModelFactory
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import com.example.lotus.utils.Resource
import com.example.lotus.utils.getTimeAgo
import kotlinx.coroutines.launch

class PostDetailFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var postAdapter: PostAdapter
    private lateinit var commentAdapter: CommentAdapter


    private var postId: String? = null

    private val postViewModel: PostViewModel by viewModels {
        PostViewModelFactory(PostRepository(requireContext()))
    }

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(requireContext()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            postId = it.getString("postId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)

        commentAdapter = CommentAdapter(postViewModel, userViewModel)
        binding.rcvComment.adapter = commentAdapter
        binding.rcvComment.layoutManager = LinearLayoutManager(context)

        postId?.let {
            postViewModel.getPostById(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            postViewModel.postById.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        //binding.frameLayout.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        // Update UI with data
                        with(binding) {
//                            Glide.with(requireContext())
//                                .load(resource.data?.image)
//                                .into(imgPost)
                            Glide.with(requireContext())
                                .load(resource.data?.user?.image)
                                .placeholder(R.drawable.avatar_default)
                                .into(avatarProfile)
//                            txtContentPost.text = resource.data?.content
//                            txtUserNamePost.text = resource.data?.user?.userName
//                            txtTimePost.text = getTimeAgo(resource.data?.createdOn?: " ")
//                            txtLike.text = resource.data?.likes?.size.toString()
//                            txtComment.text = resource.data?.comments?.size.toString()
                            //Log.d("abc", "onViewCreated: ${resource.data?.comments}")
                            commentAdapter.submitList(resource.data?.comments)

                        }
                        //binding.frameLayout.visibility = View.GONE
                    }
                    is Resource.Error -> {

                        Toast.makeText(requireContext(), "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

}
