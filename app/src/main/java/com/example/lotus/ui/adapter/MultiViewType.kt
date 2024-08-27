package com.example.lotus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.ui.adapter.dataItem.BaseItem
import com.example.lotus.ui.adapter.dataItem.Item1
import com.example.lotus.ui.adapter.dataItem.Item2
import com.example.lotus.ui.adapter.listener.OnItemClickListener

class MultiViewType(
    private val dataList: ArrayList<BaseItem>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewTypesMap: Map<Int, Pair<Int, (View) -> RecyclerView.ViewHolder>> = mapOf(
        0 to Pair(
            R.layout.item_type_1,
            { view -> Item1ViewHolder(view) }),
        1 to Pair(
            R.layout.item_type_2,
            { view -> Item2ViewHolder(view) }),
    )

    override fun getItemViewType(position: Int): Int {
        return dataList[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val (layoutId, viewHolderFactory) = viewTypesMap[viewType]
            ?: throw IllegalArgumentException("Invalid view type")
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return viewHolderFactory(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is Item1ViewHolder -> {
                val item = dataList[position] as Item1
                holder.userName.text = item.userName
                holder.txtPost.setOnClickListener {
                    listener.onPostClick()
                }
                if (item.imageUrl.isNullOrEmpty()) {
                    holder.image.setImageResource(R.drawable.avatar_default)
                } else {
                    Glide.with(holder.image.context).load(item.imageUrl).into(holder.image)
                }
            }

            is Item2ViewHolder -> {
                val item = dataList[position] as Item2
                holder.userName.text = item.name
                holder.content.text = item.content
                holder.txtLike.text = item.likes.size.toString()
                holder.txtComment.text=item.comments.size.toString()
                if (item.imageAvatar.isNullOrEmpty()) {
                    holder.image.setImageResource(R.drawable.avatar_default)
                } else {
                    Glide.with(holder.image.context).load(item.imageAvatar).into(holder.image)
                }
                if (item.imagePost.isNullOrEmpty()) {
                    holder.imagePost.visibility = View.GONE
                } else {
                    holder.imagePost.visibility = View.VISIBLE
                    Glide.with(holder.imagePost.context).load(item.imagePost).into(holder.imagePost)
                }
                holder.txtLike.setOnClickListener {
                    listener.onLikeClick(item.id)
                }
                holder.txtComment.setOnClickListener {
                    listener.onCommentClick(item.id)
                }
                holder.itemView.setOnClickListener {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class Item1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.avatarProfile)
        val userName: TextView = itemView.findViewById(R.id.txtUserName)
        val txtPost: LinearLayout = itemView.findViewById(R.id.linearLayoutPost)
    }

    class Item2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.avatarProfile)
        val userName: TextView = itemView.findViewById(R.id.txtUserNamePost)
        val content: TextView = itemView.findViewById(R.id.txtContentPost)
        val imagePost: ImageView = itemView.findViewById(R.id.imagePost)
        val txtLike: TextView = itemView.findViewById(R.id.txtLike)
        val txtComment: TextView = itemView.findViewById(R.id.txtComment)
    }

}