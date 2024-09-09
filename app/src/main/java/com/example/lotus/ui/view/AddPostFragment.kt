package com.example.lotus.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.example.lotus.R
import com.example.lotus.databinding.ActivityAddPostBinding
import com.example.lotus.databinding.FragmentAddPostBinding


class AddPostFragment : Fragment() {
    private lateinit var binding: FragmentAddPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val window: Window = requireActivity().window
        window.statusBarColor = resources.getColor(R.color.blue)

        binding = FragmentAddPostBinding.inflate(inflater, container, false)

        binding.cancelTv.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
            window.statusBarColor = resources.getColor(com.google.android.material.R.color.design_default_color_background)
        }

        binding.contentEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkContent()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return binding.root
    }

    private fun checkContent() {
        if (binding.contentEdt.text.toString().isEmpty()) {
            binding.postTv.setTextColor(resources.getColor(R.color.gray_text))
        } else {
            binding.postTv.setTextColor(resources.getColor(R.color.white))
        }
    }

}