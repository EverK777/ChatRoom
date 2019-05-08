package com.software7.chatroom.ui_ux.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.software7.chatroom.ChatRoom
import com.software7.chatroom.R
import com.software7.chatroom.models.Contacts
import com.software7.chatroom.ui_ux.viewholders.ContactsViewHolder

class ContactsAdapter (val contacts : ArrayList<Contacts>,val isModalBottom:Boolean) : RecyclerView.Adapter<ContactsViewHolder>()
  {
      interface OnSelectUserListener {
          fun onClick(position: Int)
      }

      private var onSelectUserListener: OnSelectUserListener? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContactsViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.contacts_recycler_model,p0,false)
        return ContactsViewHolder(view,onSelectUserListener!!)
    }
    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, p1: Int) {
        val contacts = contacts[p1]
        //addInitialLetter

        if(isModalBottom){
            holder.nameUserTextView.setTextColor(Color.parseColor("#ffffff"))
            holder.emailUserTextView.setTextColor(Color.parseColor("#ffffff"))
        }

        holder.initialLetterTextView.text = contacts.nameContact[0].toString()
        holder.nameUserTextView.text = contacts.nameContact
        holder.emailUserTextView.text = contacts.emailContact


    }

     fun setOnItemClickListener(onSelectUserListener: OnSelectUserListener){
        this.onSelectUserListener = onSelectUserListener
    }


}