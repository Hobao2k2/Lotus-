package com.example.lotus.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotus.R
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.FragmentProfileBinding
import com.example.lotus.ui.adapter.MultipleRecyclerViewsType
import com.example.lotus.ui.adapter.dataItem.Item1
import com.example.lotus.ui.adapter.dataItem.Item2
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import com.example.lotus.utils.SharedPrefManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(), MultipleRecyclerViewsType.OnItemClickListener {
    private lateinit var binding: FragmentProfileBinding
    private val userViewModel: UserViewModel by viewModels() {
        UserViewModelFactory(UserRepository(requireContext()))
    }

    private val items: MutableList<Any> = mutableListOf()
    private lateinit var adapter: MultipleRecyclerViewsType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        adapter = MultipleRecyclerViewsType(items, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            // Fetch user profile data
            userViewModel.getUserProfile().join()

            // Collect data from userProfile Flow
            userViewModel.userProfile.collect { userProfile ->
                userProfile?.let {
                    Log.i("test", "User Name: ${it.userName}")
                    items.clear()  // Clear existing items if needed
                    if(it.image==null){
                        items.add(Item1(R.drawable.avatar_default,null, it.userName))
                    }else {
                        items.add(Item1(null, it.image, it.userName))
                    }

                    // Add additional items as required
                    for (i in 1..6) {
                        items.add(Item2(R.drawable.icon_chat, "Name $i", "email$i@example.com"))
                    }

                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged()

                    Log.i("test", "Items size after update: ${items.size}")
                }
            }
        }

        return binding.root
    }

    // Implement the click listener methods
    override fun onUpdateProfileClick() {
        Log.i("test", "click update profile")
    }

    override fun onAddAvatarClick() {
        Log.i("test", "click add avatar")
    }

    override fun onPostClick() {
        Log.i("test", "click post")
    }
}
