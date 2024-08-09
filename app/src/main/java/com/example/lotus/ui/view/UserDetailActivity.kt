package com.example.lotus.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.ActivityUserDetailBinding
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUserDetailBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val _id=intent.getStringExtra("_id").toString()
        userViewModel.getUserById(_id)
        lifecycleScope.launch {
            userViewModel.userId.collect(){
                it?.let {
                    binding.txtUserName.text=it.userName
                    binding.txtEmailUser.text=it.email
                    when {
                        it.image != null -> {
                            Glide.with(this@UserDetailActivity)
                                .load(it.image)
                                .into(binding.avatarProfile)
                        }
                        else -> {
                            binding.avatarProfile.setImageResource(R.drawable.avatar_default)
                        }
                    }
                }
            }
        }
    }
}