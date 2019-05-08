package com.software7.chatroom.interfaces

import com.software7.chatroom.models.Message

interface OnSendMessage {
    fun onSend(message: Message)
}