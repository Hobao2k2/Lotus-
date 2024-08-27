package com.example.lotus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.databinding.ItemPostBinding
import com.example.lotus.ui.adapter.dataItem.ItemPost

class PostAdapter(private val items: MutableList<ItemPost>, private val listener: OnItemClickListener) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ItemPost>() {
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

    val differ = AsyncListDiffer(this, differCallback)

    // Cập nhật danh sách bài viết
    fun submitList(list: List<ItemPost>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class PostViewHolder(
        private val binding: ItemPostBinding,
        private val listener: OnItemClickListener
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

                // Load the avatar image
                Glide.with(itemView.context)
                    .load(item.imageAvatar ?: R.drawable.avatar_default)
                    .into(avatarProfile)

                txtUserNamePost.text = item.name
                txtContentPost.text = item.content
                txtLike.text = item.likes.size.toString()
                txtComment.text = item.comments.size.toString()

                val likeCommentSection = binding.likeCommentSection
                val params = likeCommentSection.layoutParams as ConstraintLayout.LayoutParams
                params.topToBottom = if (item.imagePost != null) R.id.imgPost else R.id.txtContentPost
                likeCommentSection.layoutParams = params
            }
        }
    }

    // Interface lắng nghe các sự kiện từ adapter
    interface OnItemClickListener {
        fun onLikeClick(position: Int)
        fun onCommentClick(position: Int)
    }
}
