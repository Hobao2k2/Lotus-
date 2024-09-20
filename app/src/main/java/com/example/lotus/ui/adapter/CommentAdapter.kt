package com.example.lotus.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.data.model.Comment
import com.example.lotus.databinding.ItemCommentBinding
import com.example.lotus.ui.viewModel.PostViewModel
import com.example.lotus.ui.viewModel.UserViewModel
import com.example.lotus.utils.getTimeAgo
import kotlinx.coroutines.launch

class CommentAdapter(
    private val postViewModel: PostViewModel,
    private val userViewModel: UserViewModel
):ListAdapter<Comment,CommentAdapter.CommentViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }

    inner class CommentViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment) {
            binding.apply {
                txtContentComment.text = comment.content
                txtUserNameComment.text = comment.user
                txtTimeComment.text = getTimeAgo(comment.createdOn)

                userViewModel.getUserById(comment.user)

                userViewModel.viewModelScope.launch {
                    userViewModel.userId.collect { user ->
                        if (user != null) {
                            Glide.with(binding.root)
                                .load(user.image)
                                .circleCrop()
                                .placeholder(R.drawable.avatar_default)
                                .into(avatarProfile)

                            txtUserNameComment.text = user.userName

                        }
                    }
                }

                }
            }
        }
    }