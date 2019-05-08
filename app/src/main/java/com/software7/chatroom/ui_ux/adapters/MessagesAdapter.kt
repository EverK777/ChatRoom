package com.software7.chatroom.ui_ux.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.software7.chatroom.R
import com.software7.chatroom.models.Message
import com.software7.chatroom.ui_ux.viewholders.MessageViewHolder

class MessagesAdapter(private val messages:ArrayList<Message> ) : RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MessageViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.messages_recyclerview_model,p0,false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message:Message = messages[position]

        if(message.sender == 0){
            holder.receptorCard.visibility = View.GONE
            holder.emisorCard.visibility = View.VISIBLE
            holder.emisorTextView.text = message.content
        }else{
            holder.emisorCard.visibility = View.GONE
            holder.receptorCard.visibility = View.VISIBLE
            holder.receptorTextView.text = message.content

        }
    }


}