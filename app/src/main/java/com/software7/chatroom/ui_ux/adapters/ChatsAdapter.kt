package com.software7.chatroom.ui_ux.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.software7.chatroom.R
import com.software7.chatroom.models.Message
import com.software7.chatroom.ui_ux.viewholders.ChatsViewHolder
import java.util.*
import kotlin.collections.ArrayList

class ChatsAdapter (val messages : ArrayList<Message>, val contactNames : ArrayList<String>) : RecyclerView.Adapter<ChatsViewHolder>() {

    private var onOpenNewChat: OnOpenNewChat ? = null

    interface OnOpenNewChat {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ChatsViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.chats_recycler_moder,p0,false)
        return ChatsViewHolder(view,onOpenNewChat!!)
    }
    override fun getItemCount(): Int {
       return messages.size
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, p1: Int) {
        val message = messages[p1]
        //addInitialLetter

        holder.initialLetterTextView.text = contactNames[p1][0].toString()
        holder.nameUserTextView.text = contactNames[p1]
        holder.lastMessageContent.text = message.content
        holder.hourLastMessage.text = message.hourMessage.toString() +":"+ message.minuteMessage.toString()
        if(message.seen == "0"){
            holder.countUnseenTextView.visibility = View.GONE
        }else{
            holder.countUnseenTextView.visibility = View.VISIBLE
            holder.countUnseenTextView.text =  message.seen
        }
    }



    fun setOnItemClickListener(onOpenNewChat: OnOpenNewChat){
        this.onOpenNewChat = onOpenNewChat
    }


}