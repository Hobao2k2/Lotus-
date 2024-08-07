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
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> ChatFragment()
            2 -> NotifyFragment()
            3 -> ProfileFragment()
            else -> HomeFragment()
        }
    }
}