package com.example.lotus.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
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
        UserViewModelFactory(UserRepository(requireContext()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotifyBinding.inflate(inflater, container, false)

        // Kiểm tra trạng thái hiện tại của chế độ night mode
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()

        // Cập nhật trạng thái của switch theo chế độ hiện tại
        binding.switchTheme.isChecked = currentNightMode == AppCompatDelegate.MODE_NIGHT_YES

        // Lắng nghe sự kiện thay đổi của switch
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Bật chế độ night mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Bật chế độ day mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        return binding.root
    }
}