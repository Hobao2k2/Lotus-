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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.lotus.R
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.FragmentLoginBinding
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import com.example.lotus.utils.Resource
import com.example.lotus.utils.SharedPrefManager
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var sharedPrefManager: SharedPrefManager

    private val loginViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefManager = SharedPrefManager(requireContext())
        RetrofitClient.initialize(context = requireContext())
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

        binding.btnLoginLotus.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_homePageActivity)
        }

        binding.tvRegister.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLoginLotus.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) binding.edtEmail.error = "Please fill out this field"
                if (password.isEmpty()) binding.edtPassword.error = "Please fill out this field"
            }
            else{
                loginViewModel.login(RegisterRequest("",email = email, password = password,""))

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loginResponse.collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        Log.d(TAG, "onCreateView: Loading")
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Success -> {
                        sharedPrefManager.saveToken(response.data!!)
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homePageActivity)
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "onCreateView: ${response.message}")
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root
    }


}