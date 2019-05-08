package com.software7.chatroom.interfaces

import com.google.firebase.database.ChildEventListener
import com.software7.chatroom.models.Message

interface OnReceiveMessageHome {

    fun onRecieve(messages:Message , contactName:String, position: Int?)
    fun asignListener(childEventListener: ChildEventListener)



}