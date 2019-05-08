package com.software7.chatroom.interfaces

import com.google.firebase.database.ChildEventListener
import com.software7.chatroom.models.Message

interface OnReceiveMessage {

    fun onRecieve(message: Message)
    fun asignListener(childEventListener: ChildEventListener)
}