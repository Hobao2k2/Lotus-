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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.lotus.R
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.data.repository.AuthRepository
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.FragmentLoginBinding
import com.example.lotus.ui.viewModel.Auth.AuthViewModel
import com.example.lotus.ui.viewModel.Auth.AuthViewModelFactory
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import com.example.lotus.utils.Resource
import com.example.lotus.utils.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var sharedPrefManager: SharedPrefManager

    private val loginViewModel: AuthViewModel by viewModels() {
        AuthViewModelFactory(AuthRepository(requireContext()))
    }

    private val userViewModel: UserViewModel by viewModels() {
        UserViewModelFactory(UserRepository(requireContext()))
    }

    private val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)  // Hiệu ứng khi fragment mới xuất hiện
        .setExitAnim(R.anim.slide_out_left)   // Hiệu ứng khi fragment hiện tại biến mất
        .setPopEnterAnim(R.anim.slide_in_left)  // Hiệu ứng khi quay lại fragment trước
        .setPopExitAnim(R.anim.slide_out_right)
        .setPopUpTo(R.id.loginFragment, false)
        .setLaunchSingleTop(true)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefManager = SharedPrefManager(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvRegister.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_registerFragment, null, navOptions)
        }

        binding.btnLoginLotus.setOnClickListener {
//            val email = binding.edtEmail.text.toString()
//            val password = binding.edtPassword.text.toString()

            val email = "thietluong2002@gmail.com"
            val password = "thiet19502"

            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) binding.edtEmail.error = "Please fill out this field"
                if (password.isEmpty()) binding.edtPassword.error = "Please fill out this field"
            }
            else{
                loginViewModel.login(RegisterRequest("",email = email, password = password,""))

                viewLifecycleOwner.lifecycleScope.launch {
                    loginViewModel.loginResponse.collect { response ->
                        when (response) {
                            is Resource.Loading -> {
                                Log.d(TAG, "Loading")
                                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Success -> {
                                Log.d(TAG, "Success: ${response.data}")

                                // Điều hướng với NavOptions
                                findNavController().navigate(R.id.action_loginFragment_to_homePageActivity, null, navOptions)


                                sharedPrefManager.saveToken(response.data!!)
                                sharedPrefManager.saveLoginState(true)

                                saveUserId()

                                Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Error -> {
                                Log.e(TAG, "Error: ${response.message}")
                                Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }


            }

        }

        return binding.root
    }

    private fun saveUserId() {
        userViewModel.getUserProfile()
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.userProfile.collect { response ->
                if (response != null) {
                    sharedPrefManager.saveUserId(response.id)

                }
            }
        }
    }


}