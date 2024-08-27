package com.example.lotus.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lotus.ui.view.ChatFragment
import com.example.lotus.ui.view.HomeFragment
import com.example.lotus.ui.view.NotifyFragment
import com.example.lotus.ui.view.ProfileFragment

class MyBottomViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList = listOf(
        HomeFragment(),
        ChatFragment(),
        NotifyFragment(),
        ProfileFragment()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun getFragmentAt(position: Int): Fragment = fragmentList[position]
}