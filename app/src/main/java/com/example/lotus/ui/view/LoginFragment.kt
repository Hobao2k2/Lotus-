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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.lotus.R
import com.example.lotus.data.model.RegisterRequest
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.FragmentLoginBinding
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import com.example.lotus.utils.Resource
import com.example.lotus.utils.SharedPrefManager

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var sharedPrefManager: SharedPrefManager

    private val loginViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(requireContext()))
    }

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

        binding.btnLoginLotus.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_homePageActivity)
        }

        binding.tvRegister.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

//        binding.btnLoginLotus.setOnClickListener {
//            val email = binding.edtEmail.text.toString()
//            val password = binding.edtPassword.text.toString()
//
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT)
//                    .show()
//            }
//            else{
//                loginViewModel.login(RegisterRequest("",email = email, password = password,""))
//
//            }
//
//        }
        loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer{ response->
            when(response){
                is Resource.Loading->{
                    Toast.makeText(requireContext(),"Loading",Toast.LENGTH_SHORT).show()
                }
                is Resource.Success->{
                    findNavController().navigate(R.id.action_loginFragment_to_homePageActivity)
                    sharedPrefManager.saveToken(response.data!!)
                }
                is Resource.Error->{
                    val errorMessage = response.message
                    Log.e(TAG, "Login error: $errorMessage")
                    Toast.makeText(context,  errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        )
        return binding.root
    }


}