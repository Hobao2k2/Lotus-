package com.example.lotus.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.data.model.FriendId
import com.example.lotus.data.model.ReceiverId
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.ActivityUserDetailBinding
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val _id = intent.getStringExtra("_id").toString()


        lifecycleScope.launch {
            userViewModel.getUserById(_id)
            userViewModel.userId.collect { user ->
                user?.let {
                    binding.txtUserName.text = it.userName
                    binding.txtEmailUser.text = it.email
                    if (it.image != null) {
                        Glide.with(this@UserDetailActivity)
                            .load(it.image)
                            .into(binding.avatarProfile)
                    } else {
                        binding.avatarProfile.setImageResource(R.drawable.avatar_default)
                    }
                }
            }
        }

        lifecycleScope.launch {
            userViewModel.getUserProfile()
            userViewModel.userProfile.collect { userProfile ->
                Log.i("test",userProfile.toString())
                userProfile?.let {
                    if(it.friendRequestsSent.contains(_id)){
                        binding.btnAddFriend.text = "Chờ phản hồi"
                    }
                    else if (it.friendRequests.contains(_id)) {
                        binding.btnAddFriend.text = "Phản hồi"
                    } else if(it.friends.contains(_id)){
                        binding.btnAddFriend.text = "Bạn bè"
                    }else{
                        binding.btnAddFriend.text = "Thêm Bạn Bè"
                    }
                }
            }
        }

        binding.btnAddFriend.setOnClickListener {
            if(binding.btnAddFriend.text.equals("Phản hồi")) {
                showOptionsDialog(_id)
            }else if(binding.btnAddFriend.text.equals("Thêm Bạn Bè")){
                binding.btnAddFriend.text = "Chờ phản hồi"
                userViewModel.addFriend(ReceiverId(_id))
            }
        }
    }

    private fun showOptionsDialog(_id:String) {
        val options = arrayOf("Xác nhận", "Từ chối")

        AlertDialog.Builder(this)
            .setItems(options) {_, which ->
                when (which) {
                    0 -> {
                        userViewModel.acceptFriend(FriendId(_id))
                        binding.btnAddFriend.setText("Bạn bè")
                        Log.d("UserDetailActivity", "Chọn xác nhận")
                    }
                    1 -> {
                        // Xử lý lựa chọn 2
                        userViewModel.rejectFriend(FriendId(_id))
                        binding.btnAddFriend.setText("Thêm bạn bè")
                        Log.d("UserDetailActivity", "Chọn từ chối")
                    }
                }
            }
            .setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
