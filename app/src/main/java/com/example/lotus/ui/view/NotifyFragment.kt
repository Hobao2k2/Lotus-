package com.example.lotus.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lotus.R
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.FragmentNotifyBinding
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NotifyFragment : Fragment() {
    private lateinit var binding:FragmentNotifyBinding
    private val userViewModel:UserViewModel by viewModels {
        UserViewModelFactory(UserRepository())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotifyBinding.inflate(inflater, container, false)
        return binding.root
    }
}