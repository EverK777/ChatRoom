package com.software7.chatroom.presenters

import com.google.firebase.database.ChildEventListener
import com.software7.chatroom.interacts.HomeChatsInteractImpl
import com.software7.chatroom.interfaces.OnGetMessages
import com.software7.chatroom.interfaces.OnReceiveMessage
import com.software7.chatroom.interfaces.OnReceiveMessageHome
import com.software7.chatroom.models.Message
import com.software7.chatroom.views.HomeChatsView

class HomeChatsPresenterImpl(private val homeChatsView: HomeChatsView) : HomeChatsPresenter, OnGetMessages, OnReceiveMessageHome {


    private var homeChatsInteract = HomeChatsInteractImpl()


    override fun getFinalMessagesPerChat() {
        homeChatsInteract.getAllMessages(this)

    }

    override fun recieveMessages(messages:ArrayList<Message>) {
        homeChatsInteract.receiveMessages(this,messages)
    }

    override fun initRecyclerView(messages: ArrayList<Message>, contactNames : ArrayList<String>) {
        homeChatsView.initRecyclerView(messages,contactNames)

    }

    override fun showEmpty() {
        homeChatsView.showEmpty()
    }

    override fun onRecieve(messages:Message,contactName:String, position: Int?) {
        homeChatsView.notifyRecyclerViewReciveMessage(messages,contactName,position)

    }


    override fun asignListener(childEventListener: ChildEventListener) {
        homeChatsView.initListener(childEventListener)
    }
}