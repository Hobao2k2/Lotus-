package com.example.lotus.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lotus.R
import com.example.lotus.ui.adapter.MyBottomViewPagerAdapter
import com.example.lotus.databinding.ActivityHomePageBinding

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomePageActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = MyBottomViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabView = LayoutInflater.from(this).inflate(R.layout.item_tab, null)
            val icon = tabView.findViewById<ImageView>(R.id.tabIcon)
            val text = tabView.findViewById<TextView>(R.id.tabText)

            when (position) {
                0 -> {
                    icon.setImageResource(R.drawable.icon_home)
                    text.text = "Trang chủ"
                }

                1 -> {
                    icon.setImageResource(R.drawable.icon_chat)
                    text.text = "Trò chuyện"
                }

                2 -> {
                    icon.setImageResource(R.drawable.icon_notify)
                    text.text = "Thông báo"
                }

                3 -> {
                    icon.setImageResource(R.drawable.icon_profile)
                    text.text = "Cá nhân"
                }
            }
            tab.customView = tabView
        }.attach()


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateTabColors(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                updateTabColors(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        // Lấy HomeFragment từ adapter
                        val homeFragment = (binding.viewPager.adapter as MyBottomViewPagerAdapter).getFragmentAt(0) as? HomeFragment
                        homeFragment?.scrollToTopAndRefresh()
                    }
                    // Xử lý các tab khác nếu cần
                }
            }
        })
        val selectedTab = binding.tabLayout.getTabAt(binding.tabLayout.selectedTabPosition)
        updateTabColors(selectedTab, true)
    }


    private fun updateTabColors(tab: TabLayout.Tab?, isSelected: Boolean) {
        val tabView = tab?.customView ?: return
        val icon = tabView.findViewById<ImageView>(R.id.tabIcon)
        val text = tabView.findViewById<TextView>(R.id.tabText)
        if (isSelected) {
            icon.setColorFilter(ContextCompat.getColor(this, R.color.blue))
            text.setTextColor(ContextCompat.getColor(this, R.color.blue))
        } else {
            icon.setColorFilter(ContextCompat.getColor(this, R.color.black))
            text.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }


}