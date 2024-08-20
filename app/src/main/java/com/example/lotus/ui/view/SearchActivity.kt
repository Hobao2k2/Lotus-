package com.example.lotus.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lotus.R
import com.example.lotus.data.repository.UserRepository
import com.example.lotus.databinding.ActivitySearchBinding
import com.example.lotus.ui.adapter.SearchUserAdapter
import com.example.lotus.ui.adapter.dataItem.ItemSearchUser
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.ui.viewModel.UserViewModelFactory
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity(), SearchUserAdapter.OnItemClickListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: SearchUserAdapter
    private val items: MutableList<ItemSearchUser> = mutableListOf()
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = SearchUserAdapter(items, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.imgSearch.setOnClickListener {
            val keyWord = binding.edtSearch.text.trim().toString()
            if (keyWord.isNotEmpty()) {
                searchUser(keyWord)
            } else {
                Toast.makeText(this, "Vui long nhap tu khoa tim kiem", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchUser(keyword: String) {
        lifecycleScope.launch {
            try {
                userViewModel.SearchUser(keyword)
                userViewModel.userAll.collect { users ->
                    users?.let {
                        items.clear()
                        it.forEach { user ->
                            items.add(ItemSearchUser(user.id,user.image, user.userName))
                        }
                        adapter.notifyDataSetChanged()
                        Log.i("test", "Items size after update: ${items.size}")
                    }
                }
            } catch (e: Exception) {
                Log.e("SearchActivity", "Failed to get users", e)
                Toast.makeText(this@SearchActivity, "Failed to get users", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(position: Int) {
        val clickedUser = items[position]
        Toast.makeText(this, "Clicked on: ${clickedUser.userName}", Toast.LENGTH_SHORT).show()
        val intent= Intent(this,UserDetailActivity::class.java)
        intent.putExtra("_id",clickedUser.id)
        startActivity(intent)
    }
}
