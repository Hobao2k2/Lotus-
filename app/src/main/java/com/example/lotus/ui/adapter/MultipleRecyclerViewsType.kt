package com.example.lotus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.data.model.Post
import com.example.lotus.ui.adapter.dataItem.Item1
import com.example.lotus.ui.adapter.dataItem.Item2
import com.example.lotus.ui.view.ProfileFragment



class MultipleRecyclerViewsType(
    private val items: MutableList<Any>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM1 = 0
        private const val TYPE_ITEM2 = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Item1 -> TYPE_ITEM1
            is Item2 -> TYPE_ITEM2
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM1 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_type_1, parent, false)
                Item1ViewHolder(view, listener)
            }
            TYPE_ITEM2 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_type_2, parent, false)
                Item2ViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is Item1ViewHolder -> holder.bind(items[position] as Item1)
            is Item2ViewHolder -> holder.bind(items[position] as Item2)
        }
    }

    fun addItem(item: Any) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(newItems: List<Any>) {
        val startPosition = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startPosition, newItems.size)
    }

    class Item1ViewHolder(
        itemView: View,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val imageAvatar: ImageView = itemView.findViewById(R.id.avatarProfile)
        private val userName: TextView = itemView.findViewById(R.id.txtUserName)
        private val updateProfile: TextView = itemView.findViewById(R.id.txtUpdateProfile)
        private val addAvatar: TextView = itemView.findViewById(R.id.txtAddAvatar)
        private val post: LinearLayout = itemView.findViewById(R.id.linearLayoutPost)

        init {
            updateProfile.setOnClickListener {
                listener.onUpdateProfileClick()
            }
            addAvatar.setOnClickListener {
                listener.onAddAvatarClick()
            }
            post.setOnClickListener {
                listener.onPostClick()
            }
        }

        fun bind(item: Item1) {
            when {
                item.imageUrl != null -> {
                    Glide.with(itemView.context)
                        .load(item.imageUrl)
                        .into(imageAvatar)
                }
                item.imageResId != null -> {
                    imageAvatar.setImageResource(item.imageResId)
                }
                else -> {
                    imageAvatar.setImageResource(R.drawable.avatar_default)
                }
            }
            userName.text = item.userName
        }
    }

    class Item2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ViewStub = itemView.findViewById(R.id.imgStub)
        private val imageView: ImageView
        private val username: TextView = itemView.findViewById(R.id.txtUserNamePost)
        private val content: TextView = itemView.findViewById(R.id.txtContentPost)
        init {
            // Inflate the ViewStub and initialize other views
            val inflatedView = image.inflate()
            imageView = inflatedView.findViewById(R.id.imgAvatarPost)
        }

        fun bind(item: Item2) {
            Glide.with(itemView.context)
                .load(item.Image)
                .into(imageView)
            username.text = item.name
            content.text = item.content
        }
    }

    interface OnItemClickListener {
        fun onUpdateProfileClick()
        fun onAddAvatarClick()
        fun onPostClick()
    }
}