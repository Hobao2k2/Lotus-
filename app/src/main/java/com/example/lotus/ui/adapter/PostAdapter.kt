package com.example.lotus.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.databinding.ItemPostBinding
import com.example.lotus.ui.adapter.dataItem.ItemPost
import com.example.lotus.utils.SharedPrefManager

class PostAdapter(
    private val userId: String?,
    private val listener: OnItemClickListener
) : ListAdapter<ItemPost, PostAdapter.PostViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemPost>() {
            override fun areItemsTheSame(oldItem: ItemPost, newItem: ItemPost): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemPost, newItem: ItemPost): Boolean {
                return oldItem.imagePost == newItem.imagePost &&
                        oldItem.imageAvatar == newItem.imageAvatar &&
                        oldItem.name == newItem.name &&
                        oldItem.content == newItem.content &&
                        oldItem.likes.size == newItem.likes.size &&
                        oldItem.comments.size == newItem.comments.size
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, listener, userId)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PostViewHolder(
        val binding: ItemPostBinding,
        private val listener: OnItemClickListener,
        private val userId: String?,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgLike.setOnClickListener {
                listener.onLikeClick(bindingAdapterPosition)
            }
            binding.imgComment.setOnClickListener {
                listener.onCommentClick(bindingAdapterPosition)
            }
        }

        fun bind(item: ItemPost) {
            with(binding) {
                if (item.imagePost != null) {
                    imgPost.visibility = View.VISIBLE
                    Glide.with(itemView.context)
                        .load(item.imagePost)
                        .into(imgPost)
                } else {
                    imgPost.visibility = View.GONE
                }

                Glide.with(itemView.context)
                    .load(item.imageAvatar ?: R.drawable.avatar_default)
                    .into(avatarProfile)

                txtUserNamePost.text = item.name
                txtContentPost.text = item.content
                txtLike.text = item.likes.size.toString()
                txtComment.text = item.comments.size.toString()

                val isLiked = item.likes.contains(userId)

                imgLike.setColorFilter(
                    itemView.resources.getColor(
                        if (isLiked) R.color.blue else R.color.graydam,
                        null
                    )
                )

                val params = likeCommentSection.layoutParams as ConstraintLayout.LayoutParams
                params.topToBottom = if (item.imagePost != null) R.id.imgPost else R.id.txtContentPost
                likeCommentSection.layoutParams = params
            }
        }
    }

    interface OnItemClickListener {
        fun onLikeClick(position: Int)
        fun onCommentClick(position: Int)
    }
}

