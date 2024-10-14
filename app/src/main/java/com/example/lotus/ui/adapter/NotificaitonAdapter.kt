package com.example.lotus.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lotus.R
import com.example.lotus.ui.adapter.dataItem.NotificationItem

class NotificaitonAdapter(val dataList: ArrayList<NotificationItem>):RecyclerView.Adapter<NotificaitonAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image:ImageView=itemView.findViewById(R.id.avatarProfile)
        val userName:TextView=itemView.findViewById(R.id.txtUserName)
        val message:TextView=itemView.findViewById(R.id.txtMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificaitonAdapter.ViewHolder {
        val itemView:View=LayoutInflater.from(parent.context).inflate(R.layout.item_notificaiton,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=dataList[position]
        holder.userName.text = item.userName
        holder.message.text = item.message
        if (!item.image.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(item.image)
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.avatar_default)
        }


    }



    override fun getItemCount(): Int {
        return dataList.size
    }
}