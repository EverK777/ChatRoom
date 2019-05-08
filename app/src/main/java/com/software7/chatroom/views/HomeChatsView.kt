package com.software7.chatroom.views

import com.google.firebase.database.ChildEventListener
import com.software7.chatroom.models.Message

interface HomeChatsView {

    fun initRecyclerView(messages: ArrayList<Message>,contactNames : ArrayList<String>)
    fun notifyRecyclerViewReciveMessage(messages:Message,contactName:String, position: Int?)
    fun initListener(childEventListener : ChildEventListener)
    fun showEmpty()

}