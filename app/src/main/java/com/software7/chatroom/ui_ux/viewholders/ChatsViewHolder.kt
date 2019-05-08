package com.software7.chatroom.ui_ux.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.software7.chatroom.R
import com.software7.chatroom.ui_ux.adapters.ChatsAdapter


class ChatsViewHolder (itemView: View,onOpenNewChat: ChatsAdapter.OnOpenNewChat) :  RecyclerView.ViewHolder(itemView){

    val containerLetterFrameLayout : FrameLayout = itemView.findViewById(R.id.circleContainer)
    val initialLetterTextView   : TextView = itemView.findViewById(R.id.initialName)
    val nameUserTextView : TextView = itemView.findViewById(R.id.userNameTextView)
    val hourLastMessage : TextView = itemView.findViewById(R.id.hourTextView)
    val lastMessageContent : TextView = itemView.findViewById(R.id.lastMessageTextView)
    val countUnseenTextView  :TextView = itemView.findViewById(R.id.countUnSeen)

    init{
        itemView.setOnClickListener {
            if(adapterPosition != RecyclerView.NO_POSITION){
                onOpenNewChat.onClick(adapterPosition)
            }
        }
    }

}