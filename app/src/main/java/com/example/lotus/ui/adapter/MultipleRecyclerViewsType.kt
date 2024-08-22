package com.example.lotus.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.databinding.ItemPostBinding
import com.example.lotus.databinding.ItemType1Binding
import com.example.lotus.databinding.ItemType2Binding
import com.example.lotus.ui.adapter.dataItem.ItemProfile
import com.example.lotus.ui.adapter.dataItem.ItemPost
import com.example.lotus.ui.adapter.dataItem.ListItem


class MultipleRecyclerViewsType(
    private val items: MutableList<ListItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return items[position].getType().value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ListItem.Type.values().first { it.value == viewType }) {
            ListItem.Type.PROFILE -> {
                val binding = ItemType1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProfileViewHolder(binding, listener)
            }
            ListItem.Type.POST -> {
                val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PostViewHolder(binding, listener)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder -> holder.bind(items[position] as ItemProfile)
            is PostViewHolder -> holder.bind(items[position] as ItemPost)
        }
    }

    class ProfileViewHolder(
        private val binding: ItemType1Binding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.txtUpdateProfile.setOnClickListener {
                listener.onUpdateProfileClick()
            }
            binding.txtAddAvatar.setOnClickListener {
                listener.onAddAvatarClick()
            }
            binding.linearLayoutPost.setOnClickListener {
                listener.onPostClick()
            }
        }

        fun bind(item: ItemProfile) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(item.imageUrl ?: R.drawable.avatar_default)
                    .into(avatarProfile)
                txtUserName.text = item.userName
            }
        }
    }

    class PostViewHolder(
        private val binding: ItemPostBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private var isViewStubInflated = false
        private var imageView: ImageView? = null

        init {
            binding.imgLike.setOnClickListener {
                listener.onLikeClick(bindingAdapterPosition.toString())
            }
            binding.imgComment.setOnClickListener {
                listener.onCommentClick(bindingAdapterPosition.toString())
            }
        }

        fun bind(item: ItemPost) {
            with(binding) {
                // Inflate the ViewStub and get the reference to the ImageView
                if (!isViewStubInflated) {
                    val inflatedView = imgStub.inflate()
                    imageView = inflatedView.findViewById(R.id.imgAvatarPost)
                    isViewStubInflated = true
                }

                // Load image using Glide into the inflated ImageView
                imageView?.let {
                    Glide.with(itemView.context)
                        .load(item.imagePost)
                        .into(it)
                }

                // Load the avatar image
                Glide.with(itemView.context)
                    .load(item.imageAvatar ?: R.drawable.avatar_default)
                    .into(avatarProfile)

                txtUserNamePost.text = item.name
                txtContentPost.text = item.content
                txtLike.text = item.likes.size.toString()
                txtComment.text = item.comments.size.toString()
            }
        }
    }

    interface OnItemClickListener {
        fun onUpdateProfileClick()
        fun onAddAvatarClick()
        fun onPostClick()
        fun onLikeClick(id: String)
        fun onCommentClick(id: String)
    }
}
