package com.software7.chatroom.interfaces

import com.software7.chatroom.models.Message

interface OnGetMessages {

    fun initRecyclerView(messages: ArrayList<Message>, contactNames : ArrayList<String>)
    fun showEmpty()
}