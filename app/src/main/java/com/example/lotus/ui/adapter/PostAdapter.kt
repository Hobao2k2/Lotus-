package com.example.lotus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.data.model.Post
import com.example.lotus.databinding.FragmentHomePostBinding


class PostAdapter(private val postList:  List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    class ViewHolder(private val binding: FragmentHomePostBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isImage1 = true
         var like: Int =0
        fun bind(post: Post) {
              like = post.likes.size
            Glide.with(binding.avata.context)
                .load(post.user.image)
                .into(binding.avata)
            binding.name.text = post.user.userName
            binding.status.text = post.content

            Glide.with(binding.image.context)
                .load(post.image)
                .into(binding.image)
            setImage() // Đặt hình ảnh ban đầ
            binding.buttonLike.setOnClickListener {
                isImage1 = !isImage1 // Đổi trạng thái hình ảnh
                setImage()
            }

            binding.like.text = like.toString()
        }
        private fun setImage() {
            val imageResId = if (isImage1){
                R.drawable.icon_unlike
            } else{
                R.drawable.icon_like
            }
            binding.buttonLike.setBackgroundResource(imageResId)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentHomePostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}