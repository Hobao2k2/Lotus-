package com.example.lotus.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lotus.R
import com.example.lotus.data.repository.PostRepository
import com.example.lotus.databinding.FragmentPostDetailBinding
import com.example.lotus.ui.adapter.PostAdapter
import com.example.lotus.ui.viewModel.PostViewModel
import com.example.lotus.ui.viewModel.PostViewModelFactory

class PostDetailFragment : Fragment() {

    private lateinit var binding : FragmentPostDetailBinding
    private lateinit var adapter: PostAdapter
    private var postId: String? = null

    private val postViewModel: PostViewModel by viewModels() {
        PostViewModelFactory(PostRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            postId = it.getString("postId")
        }

        if(postId == null){
            requireActivity().onBackPressed()
        }

        binding = FragmentPostDetailBinding.inflate(inflater, container, false)



        return binding.root
    }


}