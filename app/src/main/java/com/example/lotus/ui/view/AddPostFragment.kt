package com.example.lotus.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lotus.R
import com.example.lotus.databinding.ActivityAddPostBinding
import com.example.lotus.databinding.FragmentAddPostBinding


class AddPostFragment : Fragment() {
    private lateinit var binding: FragmentAddPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("abc", "onCreateView: abc")

        binding = FragmentAddPostBinding.inflate(inflater, container, false)

        binding.cancelBtn.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }

}