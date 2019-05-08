package com.software7.chatroom.views

import com.software7.chatroom.models.Contacts
import com.software7.chatroom.models.Message

interface HomeContactView {

    fun initRecyclerView(contacts: ArrayList<Contacts>)
    fun showEmpty()
}