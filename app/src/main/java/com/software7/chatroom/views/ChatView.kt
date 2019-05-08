package com.software7.chatroom.views

import com.google.firebase.database.ChildEventListener
import com.software7.chatroom.models.Message

interface ChatView {

    fun notifyRecyclerViewSendMessage(message: Message)
    fun notifyRecyclerViewReciveMessage(message: Message)
    fun fillRecyclerView(messages:ArrayList<Message>)
    fun initListener(childEventListener: ChildEventListener)

}