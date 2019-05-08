package com.software7.chatroom.ui_ux.viewholders

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import com.software7.chatroom.R

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //emisor
    val emisorCard : CardView = itemView.findViewById(R.id.emisorCard)
    val emisorTextView : AppCompatTextView = itemView.findViewById(R.id.emisorMessage)

    //receptor
    val receptorCard : CardView = itemView.findViewById(R.id.receptorCard)
    val receptorTextView : AppCompatTextView = itemView.findViewById(R.id.receptorMessage)

}