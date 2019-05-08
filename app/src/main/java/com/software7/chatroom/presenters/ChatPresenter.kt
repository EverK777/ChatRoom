package com.software7.chatroom.presenters

import com.software7.chatroom.models.Message

interface ChatPresenter {

    fun sendMessage(message:String)
    fun recieveMessages()
    fun initRecycler()

}