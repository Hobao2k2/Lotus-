package com.example.lotus.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lotus.R
import com.example.lotus.data.network.RetrofitClient
import com.example.lotus.data.network.RetrofitInstance
import com.example.lotus.ui.adapter.PostAdapter
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var itemCountTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        postAdapter = PostAdapter(listOf())
        recyclerView.adapter = postAdapter
// Fetch posts từ API và cập nhật adapter
        fetchPosts()
    }
    private fun fetchPosts() {
        lifecycleScope.launch {
            try {
                // Thay "your_token_here" bằng token thực tế
                val posts = RetrofitInstance.api.getAllPost("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRW1haWwiOiJidWltYW5kYWMxNDAyQGdtYWlsLmNvbSIsInVzZXJJZCI6IjY2YjE5N2M1YjgyMjk5ZTRiOWViNjA5NiIsImlhdCI6MTcyMjkzODM3MSwiZXhwIjoxNzIyOTQxOTcxfQ.JIDXTB1nXseDa4y0I8VUUDoRelWTkwCUp8o2w_Y8H7o")
                postAdapter = PostAdapter(posts)  // Tạo adapter mới với danh sách post mới
                view?.findViewById<RecyclerView>(R.id.recyclerView)?.adapter = postAdapter // Cập nhật adapter của RecyclerView
            } catch (e: Exception) {
                e.printStackTrace() // Xử lý lỗi ở đây
            }
        }
    }
}