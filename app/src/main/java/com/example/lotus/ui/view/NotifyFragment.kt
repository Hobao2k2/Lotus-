package com.example.lotus.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotus.R
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.FragmentNotifyBinding
import com.example.lotus.ui.adapter.NotificaitonAdapter
import com.example.lotus.ui.adapter.dataItem.NotificationItem
import com.example.lotus.ui.viewModel.NotificationVIewModel
import com.example.lotus.ui.viewModel.NotificationViewModelFactory
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NotifyFragment : Fragment() {
    private lateinit var binding:FragmentNotifyBinding
    private lateinit var item:ArrayList<NotificationItem>
    private val notificationViewModel:NotificationVIewModel by viewModels {
        NotificationViewModelFactory(UserRepository(requireContext()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotifyBinding.inflate(inflater, container, false)
        item = ArrayList()
        val adapter = NotificaitonAdapter(item) // Lưu adapter trong một biến cục bộ
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            notificationViewModel.getNotification()
            notificationViewModel.notification.collect() { notifications ->
                notifications?.let {
                    Log.i("notification size", it.size.toString())
                    it.forEach { notification ->
                        Log.i("iii",notification.notificationSender.image.toString())
                        item.add(NotificationItem(notification.notificationSender.image ?: "",notification.notificationSender.userName,notification.message))
                    }
                    adapter.notifyDataSetChanged()
                }
            }


        }

        return binding.root
    }
}