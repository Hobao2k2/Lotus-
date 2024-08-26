package com.example.lotus.ui.view

import android.content.Intent
import android.icu.text.Transliterator.Position
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import com.example.lotus.R
import com.example.lotus.data.model.Post
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.FragmentProfileBinding
import com.example.lotus.ui.adapter.MultipleRecyclerViewsType
import com.example.lotus.ui.adapter.dataItem.Item1
import com.example.lotus.ui.adapter.dataItem.Item2
import com.example.lotus.ui.adapter.itemDecoration.ItemOffsetDecoration
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import com.example.lotus.utils.SharedPrefManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(), MultipleRecyclerViewsType.OnItemClickListener {
    private lateinit var binding: FragmentProfileBinding
    private var image: String? = null

    private var username: String? = null
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
        adapter = MultipleRecyclerViewsType(items, this, userViewModel.postLikes)
        binding.recyclerView.adapter = adapter
        var dividerItemDecoration=DividerItemDecoration(requireContext(),RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources,R.drawable.divider_item_post,null)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        val itemOffsetDecoration=ItemOffsetDecoration()
        binding.recyclerView.addItemDecoration(itemOffsetDecoration)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            // Fetch user profile data
            userViewModel.getUserProfile().join()

            // Collect data from userProfile Flow
            userViewModel.userProfile.collect { userProfile ->
                userProfile?.let {
                    image=it.image
                    username=it.userName
                    items.clear()  // Clear existing items if needed
                    items.add(Item1( it.image, it.userName))
                    adapter.notifyDataSetChanged()

                    Log.i("test", "Items size after update: ${items.size}")
                }
            }
        }
        lifecycleScope.launch {
            userViewModel.getAllPost().join()
            userViewModel.postAll.collect { posts ->
                posts?.forEach { post ->
                    items.add(
                        Item2(
                            post.user.image,
                            post.image,
                            post.user.userName ?: "Default Name",
                            post.content ?: "",
                            post.id,
                            post.likes,
                            post.comments
                        )
                    )
                }
                adapter.notifyDataSetChanged()
                Log.i("test", "Items size after update: ${items.size}")
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
        val intent = Intent(requireContext(), AddPostActivity::class.java).apply {
            putExtra("image", image)
            putExtra("username", username)
        }
        startActivity(intent)
    }

    override fun onLikeClick(id: String) {
        userViewModel.likePost(id)
    }


    override fun onCommentClick(id:String) {
        TODO("Not yet implemented")
    }
}