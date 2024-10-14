package com.example.lotus.ui.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.lotus.R
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.model.User
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.data.repository.AuthRepository
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.FragmentRegisterBinding
import com.example.lotus.ui.viewModel.Auth.AuthViewModel
import com.example.lotus.ui.viewModel.Auth.AuthViewModelFactory
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import com.example.lotus.utils.Resource
import com.example.lotus.utils.SharedPrefManager
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var sharedPrefManager: SharedPrefManager

    private val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)  // Hiệu ứng khi fragment mới xuất hiện
        .setExitAnim(R.anim.slide_out_left)   // Hiệu ứng khi fragment hiện tại biến mất
        .setPopEnterAnim(R.anim.slide_in_left)  // Hiệu ứng khi quay lại fragment trước
        .setPopExitAnim(R.anim.slide_out_right)

    private val registerViewModel: AuthViewModel by viewModels() {
        AuthViewModelFactory(AuthRepository(requireContext()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefManager = SharedPrefManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnLogin.setOnClickListener {
            val navOptions = navOptions
                .setPopUpTo(R.id.registerFragment, true) // Xóa register khỏi stack
                .setLaunchSingleTop(true)                // Không khởi tạo lại nếu đã tồn tại trên top
                .build()

            findNavController().navigate(R.id.action_registerFragment_to_loginFragment, null, navOptions)

        }

        binding.btnSignup.setOnClickListener {
            val userName = binding.edtUserName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val conformPassword = binding.edtCfPassword.text.toString()

            if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || conformPassword.isEmpty()) {
                if (userName.isEmpty()) binding.edtUserName.error = "Please fill out this field"
                if (email.isEmpty()) binding.edtEmail.error = "Please fill out this field"
                if (password.isEmpty()) binding.edtPassword.error = "Please fill out this field"
                if (conformPassword.isEmpty()) binding.edtCfPassword.error = "Please fill out this field"
            } else {
                if (password != conformPassword) {
                    binding.edtCfPassword.error = "Password does not match"
                } else {
                    val registerRequest = RegisterRequest(userName, email, password, conformPassword)
                    registerViewModel.register(registerRequest)

                    viewLifecycleOwner.lifecycleScope.launch {
                        registerViewModel.registerResponse?.collect{ response ->
                            when(response){
                                is Resource.Loading -> {
                                    Log.d(TAG, "onCreateView: Loading")
                                }
                                is Resource.Success -> {
                                    Log.d(TAG, "onCreateView: Success")
                                    val navOptions = navOptions
                                        .setPopUpTo(R.id.registerFragment, true) // Xóa register và các fragment trước đó
                                        .setLaunchSingleTop(true)                // Không khởi tạo lại nếu đã tồn tại trên top
                                        .build()

                                    findNavController().navigate(R.id.action_registerFragment_to_homePageActivity, null, navOptions)

                                    sharedPrefManager.saveLoginState(true)
                                    sharedPrefManager.saveToken(response.data!!.token)
                                }
                                is Resource.Error -> {
                                    Log.d(TAG, "onCreateView: Error")
                                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                }
            }
        }


        }


        return binding.root

    }


}