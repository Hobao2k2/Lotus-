package com.example.lotus.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.ActivityAddPostBinding
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import kotlinx.coroutines.launch
import java.io.File

class AddPostActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddPostBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(this))
    }
    private var selectedImageFile: File? = null
    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        galleryUri?.let {
        try{
            binding.imageView.setImageURI(it)
            val file = File(it.path ?: "")
            selectedImageFile = file
        }catch(e:Exception){
            e.printStackTrace()
        }}

    }


    private val imagePickerLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { uri ->
                    // Chuyển Uri thành File
                    val file = File(uri.path ?: return@let)
                    selectedImageFile = file
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lifecycleScope.launch {
            userViewModel.post.collect { post ->
                if (post != null) {
                    // Cập nhật UI với dữ liệu
                    binding.textViewContent.text = post.content
//                    binding.textViewUser.text = post.user
                    // Xử lý hình ảnh nếu có
                    post.image?.let { imageUrl ->
                        // Ví dụ: sử dụng Glide để tải hình ảnh từ URL
                        Glide.with(this@AddPostActivity)
                            .load(imageUrl)
                            .into(binding.imageView)
                    }
                } else {
                    // Xử lý trường hợp không có dữ liệu (hoặc lỗi)
                }
            }
        }

        // Ví dụ: gọi addPost khi một hành động xảy ra, chẳng hạn như khi nhấn nút
        binding.buttonPost.setOnClickListener {
            val content = "Alo Alo 1234"
            val user = "66b078fcb82299e4b9eb6036"

            userViewModel.addPost(content, user, selectedImageFile)
        }
        binding.buttonSelectImage.setOnClickListener {
//            val intent = Intent(Intent.ACTION).apply {
//                type = "image/*"
//            }

            galleryLauncher.launch("image/*")
//            imagePickerLauncher.launch(intent)
        }
    }
}