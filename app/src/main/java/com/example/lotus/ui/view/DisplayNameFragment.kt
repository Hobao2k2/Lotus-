package com.example.lotus.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lotus.databinding.FragmentDisplayNameBinding

class DisplayNameFragment : Fragment() {

    private lateinit var binding: FragmentDisplayNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayNameBinding.inflate(inflater, container, false)

        return binding.root
    }

}