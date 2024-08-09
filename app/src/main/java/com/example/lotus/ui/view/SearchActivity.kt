package com.example.lotus.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
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

class SearchActivity : AppCompatActivity() {
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

        adapter = SearchUserAdapter(items)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        binding.imgSearch.setOnClickListener {
            val keyWord:String =binding.edtSearch.text.trim().toString()
            lifecycleScope.launch {
                userViewModel.SearchUser(keyWord)
                Log.i("test111",keyWord)
                userViewModel.userAll.collect { users ->
                    users?.let {
                        items.clear()
                        it.forEach { user ->
                            items.add(ItemSearchUser(user.image, user.userName))
                        }
                        adapter.notifyDataSetChanged()
                        Log.i("test", "Items size after update: ${items.size}")
                    }
                }
            }

        }
    }
}

