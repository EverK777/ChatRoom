package com.software7.chatroom.presenters

import com.google.firebase.database.ChildEventListener
import com.software7.chatroom.interacts.ChatInteract
import com.software7.chatroom.interacts.ChatInteractImpl
import com.software7.chatroom.interfaces.OnFillRecyclerView
import com.software7.chatroom.interfaces.OnReceiveMessage
import com.software7.chatroom.interfaces.OnSendMessage
import com.software7.chatroom.models.Message
import com.software7.chatroom.views.ChatView

class ChatPresenterImpl(private val chatView: ChatView,  idContact:String) : ChatPresenter,OnReceiveMessage, OnSendMessage, OnFillRecyclerView {



    private val chatInteract: ChatInteract = ChatInteractImpl(idContact)

    override fun recieveMessages() {
      chatInteract.receiveMessages(this)
    }

    override fun sendMessage(message: String) {
        chatInteract.sendMessage(this,message)
    }
    override fun initRecycler() {
        chatInteract.initRecyclerView(this)
    }

    // methods from the implementation

    override fun onRecieve(message: Message) {
        chatView.notifyRecyclerViewReciveMessage(message)
    }

    override fun onSend(message: Message) {
        chatView.notifyRecyclerViewSendMessage(message)
    }

    override fun onFill(messages: ArrayList<Message>) {
        chatView.fillRecyclerView(messages)
    }

    override fun asignListener(childEventListener: ChildEventListener) {
        chatView.initListener(childEventListener)

    }
}