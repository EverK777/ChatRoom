package com.software7.chatroom.interfaces

import com.software7.chatroom.models.Message

interface OnFillRecyclerView {

    fun onFill(messages:ArrayList<Message>)
}