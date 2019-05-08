package com.software7.chatroom.presenters

import com.software7.chatroom.interacts.HomeChatsInteractImpl
import com.software7.chatroom.interacts.HomeContactsInteractImpl
import com.software7.chatroom.interfaces.OnGetContacts
import com.software7.chatroom.models.Contacts
import com.software7.chatroom.views.HomeContactView

class HomeContactsPresenterImpl (val homeContactView: HomeContactView):HomeContactsPresenter, OnGetContacts {

    private var homeContactsInteract = HomeContactsInteractImpl()

    override fun getContacts() {

        homeContactsInteract.getContacts(this)
    }

    override fun initRecyclerView(contacts: ArrayList<Contacts>) {
      homeContactView.initRecyclerView(contacts)
    }

    override fun showEmpty() {
        homeContactView.showEmpty()
    }
}