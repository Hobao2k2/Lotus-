package com.example.lotus.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.data.model.Post
import com.example.lotus.databinding.ItemPostBinding
import com.example.lotus.ui.adapter.dataItem.ItemPost
import com.example.lotus.utils.SharedPrefManager
import com.example.lotus.utils.getTimeAgo
import com.google.android.material.bottomsheet.BottomSheetDialog

class PostAdapter(
    private val userId: String?,
    private val listener: OnItemClickListener
) : ListAdapter<Post, PostAdapter.PostViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return  oldItem.id == newItem.id &&
                        oldItem.image == newItem.image &&
                        oldItem.user.image == newItem.user.image &&
                        oldItem.user.userName == newItem.user.userName &&
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
        val post = getItem(position)
        holder.bind(post)
    }

    class PostViewHolder(
        val binding: ItemPostBinding,
        private val listener: OnItemClickListener,
        private val userId: String?,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }

            binding.imgLike.setOnClickListener {
                listener.onLikeClick(bindingAdapterPosition)
            }

            binding.imgComment.setOnClickListener {
                listener.onCommentClick(bindingAdapterPosition)
            }

            binding.btnOption.setOnClickListener {
                showBottomSheetDialog(it.context, bindingAdapterPosition)
            }

        }

        fun bind(item: Post) {
            with(binding) {
                if (item.image != null) {
                    imgPost.visibility = View.VISIBLE
                    Glide.with(itemView.context)
                        .load(item.image)
                        .into(imgPost)
                } else {
                    imgPost.visibility = View.GONE
                }

                Glide.with(itemView.context)
                    .load(item.user.image ?: R.drawable.avatar_default)
                    .into(avatarProfile)

                txtUserNamePost.text = item.user.userName
                txtContentPost.text = item.content
                txtLike.text = item.likes.size.toString()
                txtComment.text = item.comments.size.toString()

                txtTimePost.text = getTimeAgo(item.createdOn)

                setLikeStatus(item.likes.contains(userId))

                adjustLayoutBasedOnImage(item.image)
            }
        }

        private fun setLikeStatus(isLiked: Boolean) {
            val color = if (isLiked) R.color.blue else R.color.graydam
            binding.imgLike.setColorFilter(itemView.resources.getColor(color, null))
        }

        private fun adjustLayoutBasedOnImage(imagePost: String?) {
            val params = binding.likeCommentSection.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = if (imagePost != null) R.id.imgPost else R.id.txtContentPost
            binding.likeCommentSection.layoutParams = params
        }

        private fun showBottomSheetDialog(context: Context, position: Int) {
            val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
            val view = LayoutInflater.from(context).inflate(R.layout.item_post_option, null)
            bottomSheetDialog.setContentView(view)

            val btnEditPost = view.findViewById<Button>(R.id.btnEditPost)
            val btnDeletePost = view.findViewById<Button>(R.id.btnDeletePost)
            val btnSharePost = view.findViewById<Button>(R.id.btnSharePost)
            val btnSavePost = view.findViewById<Button>(R.id.btnSavePost)

            // Khi nhấn vào "Chỉnh sửa"
            btnEditPost.setOnClickListener {
                // Chuyển đến EditPostFragment
                listener.onEditPostClick(position)
                bottomSheetDialog.dismiss()
            }

            // Khi nhấn vào "Xóa"
            btnDeletePost.setOnClickListener {
                // Hiển thị thông báo xác nhận xóa
                AlertDialog.Builder(context)
                    .setMessage("Bạn có chắc muốn xóa bài viết này không?")
                    .setPositiveButton("Đồng ý") { _, _ ->
                        listener.onDeletePostClick(position)
                    }
                    .setNegativeButton("Hủy") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                bottomSheetDialog.dismiss()
            }

            // Khi nhấn vào "Chia sẻ"
            btnSharePost.setOnClickListener {
                // Chuyển đến ShareFragment
                listener.onSharePostClick(position)
                bottomSheetDialog.dismiss()
            }

            // Khi nhấn vào "Lưu"
            btnSavePost.setOnClickListener {
                listener.onSavePostClick(position)
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onLikeClick(position: Int)
        fun onCommentClick(position: Int)
        fun onEditPostClick(position: Int)
        fun onDeletePostClick(position: Int)
        fun onSharePostClick(position: Int)
        fun onSavePostClick(position: Int)
    }
}

