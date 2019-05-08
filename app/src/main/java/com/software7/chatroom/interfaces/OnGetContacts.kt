package com.software7.chatroom.interfaces

import com.software7.chatroom.models.Contacts

interface OnGetContacts {

    fun initRecyclerView(contacts: ArrayList<Contacts>)
    fun showEmpty()
}