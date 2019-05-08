package com.software7.chatroom.interacts

import com.software7.chatroom.interfaces.OnFillRecyclerView
import com.software7.chatroom.interfaces.OnReceiveMessage
import com.software7.chatroom.interfaces.OnSendMessage
import com.software7.chatroom.models.Message

interface ChatInteract {
    fun sendMessage(onSendMessage: OnSendMessage,message:String)
    fun receiveMessages(onReceiveMessage: OnReceiveMessage)
    fun initRecyclerView(onFillRecyclerView: OnFillRecyclerView)
}