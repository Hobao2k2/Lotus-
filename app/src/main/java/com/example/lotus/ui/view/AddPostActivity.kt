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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancelBtn.setOnClickListener{
            finish()
        }

    }

}