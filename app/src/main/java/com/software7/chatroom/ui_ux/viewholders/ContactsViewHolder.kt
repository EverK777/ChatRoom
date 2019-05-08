package com.software7.chatroom.ui_ux.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.software7.chatroom.R
import com.software7.chatroom.ui_ux.adapters.ContactsAdapter

class ContactsViewHolder (itemView: View, onSelectUser: ContactsAdapter.OnSelectUserListener) :  RecyclerView.ViewHolder(itemView){

    val containerLetterFrameLayout : FrameLayout = itemView.findViewById(R.id.circleContainer)
    val initialLetterTextView   : TextView = itemView.findViewById(R.id.initialName)
    val nameUserTextView : TextView = itemView.findViewById(R.id.contactNameTextView)
    val emailUserTextView : TextView = itemView.findViewById(R.id.emailUserTextView)

    init{
        itemView.setOnClickListener {
            if(adapterPosition != RecyclerView.NO_POSITION){
                onSelectUser.onClick(adapterPosition)
            }
        }
    }

}