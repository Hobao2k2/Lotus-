package com.example.lotus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.ui.adapter.dataItem.ItemComment

class CommentAdapter(private val dataList:ArrayList<ItemComment>):RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val itemView:View= LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        val item=dataList[position]
        holder.contentComment.text=item.content
        if(item.image.isNullOrEmpty()){
            holder.image.setImageResource(R.drawable.avatar_default)
        }else{
            Glide.with(holder.itemView.context).load(item.image).into(holder.image)
        }
        holder.contentComment.text=item.content
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image:ImageView=itemView.findViewById(R.id.avatarProfile)
        val userName:TextView=itemView.findViewById(R.id.txtUserName)
        val contentComment:TextView=itemView.findViewById(R.id.txtContentComment)
    }
}