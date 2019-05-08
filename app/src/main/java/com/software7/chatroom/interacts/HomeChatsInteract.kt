package com.software7.chatroom.interacts

import com.software7.chatroom.interfaces.OnGetMessages
import com.software7.chatroom.interfaces.OnReceiveMessage
import com.software7.chatroom.interfaces.OnReceiveMessageHome
import com.software7.chatroom.models.Message

interface HomeChatsInteract {
    fun getAllMessages(onGetMessages: OnGetMessages)
    fun receiveMessages(onReceiveMessageHome: OnReceiveMessageHome,messages:ArrayList<Message>)
}