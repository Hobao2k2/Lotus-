package com.example.lotus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.ui.adapter.dataItem.ItemSearchUser

class SearchUserAdapter(private val items: MutableList<ItemSearchUser>):RecyclerView.Adapter<SearchUserAdapter.ViewHolderClass>() {
    class ViewHolderClass(itemView: View):RecyclerView.ViewHolder(itemView){
        val Image: ImageView =itemView.findViewById(R.id.avatarProfile)
        val Name: TextView =itemView.findViewById(R.id.txtUserName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView:View= LayoutInflater.from(parent.context).inflate(R.layout.item_search_user,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val user = items[position]
        holder.Name.text = user.userName

        if (user.imageAvatar != null) {
            Glide.with(holder.itemView.context)
                .load(user.imageAvatar)
                .into(holder.Image)
        } else {
            holder.Image.setImageResource(R.drawable.avatar_default)
        }
    }

}