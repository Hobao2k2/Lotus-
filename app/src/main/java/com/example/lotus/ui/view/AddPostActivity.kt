package com.example.lotus.ui.view

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.ActivityAddPostBinding
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AddPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(this))
    }
    private var selectedImageFile: File? = null

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val file = uriToFile(it, this)
                selectedImageFile = file
                binding.imageView.setImageURI(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.buttonPost.setOnClickListener {
            val content = binding.edtContent.text.trim().toString()
            userViewModel.addPost(content, selectedImageFile)
            val intent = Intent(this, HomePageActivity::class.java)
            intent.putExtra("open_profile_tab", true)
            startActivity(intent)
        }

        binding.buttonSelectImage.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File? {
        // Sử dụng DocumentFile để lấy tên file từ uri
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (cursor.moveToFirst()) {
                val displayName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
                val tempFile = File.createTempFile(displayName, null, context.cacheDir)
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    tempFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                return tempFile
            }
        }
        return null
    }

}
