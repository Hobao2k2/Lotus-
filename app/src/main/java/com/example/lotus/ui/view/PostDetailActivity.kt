package com.example.lotus.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.databinding.ActivityPostDetailBinding
import com.example.lotus.ui.adapter.CommentAdapter
import com.example.lotus.ui.adapter.SearchUserAdapter
import com.example.lotus.ui.adapter.dataItem.Item2
import com.example.lotus.ui.adapter.dataItem.ItemComment

class PostDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var adapter: CommentAdapter
    private lateinit var items:ArrayList<ItemComment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val item: Item2? = intent.getParcelableExtra("item_key")
        setData(item)
        adapter = CommentAdapter(items)
        binding.recyclerViewPostDetail.adapter = adapter
        binding.recyclerViewPostDetail.layoutManager = LinearLayoutManager(this)
    }
    private fun setData(item:Item2?){
        binding.txtContentPost.text=item?.content
        binding.txtLike.text=item?.likes?.size.toString()
        binding.txtComment.text=item?.comments?.size.toString()
        binding.txtUserNamePost.text=item?.name
        if(item?.imagePost.isNullOrEmpty()){
            binding.imagePost.setImageResource(R.drawable.avatar_default)
        }else{
            Glide.with(this).load(item?.imagePost).into(binding.imagePost)
        }
        if(item?.imageAvatar.isNullOrEmpty()){
            binding.avatarProfile.setImageResource(R.drawable.avatar_default)
        }else{
            Glide.with(this).load(item?.imageAvatar).into(binding.avatarProfile)
        }
        items= arrayListOf()
        item?.comments?.forEach {
            items.add(ItemComment(it.content,item.imageAvatar,item.name))
        }
    }
}