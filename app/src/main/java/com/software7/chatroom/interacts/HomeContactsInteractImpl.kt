package com.software7.chatroom.interacts

import android.widget.Toast
import com.software7.chatroom.ChatRoom
import com.software7.chatroom.data.InternalDataBase
import com.software7.chatroom.interfaces.OnGetContacts
import com.software7.chatroom.models.Contacts
import com.software7.chatroom.models.Message

class HomeContactsInteractImpl : HomeContactsInteract {


    override fun getContacts(onGetContacts: OnGetContacts) {
        val contacts : ArrayList<Contacts> = ArrayList()
        val data  = InternalDataBase(ChatRoom.context!!)

        val cursor =data.getContacts()

        if(cursor.moveToFirst()){
            for(i in 0 until cursor.count){
                contacts.add(Contacts(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)))
                cursor.moveToNext()
            }
            onGetContacts.initRecyclerView(contacts)
        }else{
            onGetContacts.showEmpty()
        }
    }
}