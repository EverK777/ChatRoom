package com.software7.chatroom.presenters

import com.software7.chatroom.models.Message

interface HomeChatsPresenter {

    fun getFinalMessagesPerChat()
    fun recieveMessages(messages:ArrayList<Message>)
}