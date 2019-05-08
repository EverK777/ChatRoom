package com.software7.chatroom.presenters

import com.software7.chatroom.interacts.AddNewUserInteract
import com.software7.chatroom.interacts.AddNewUserInteractImpl
import com.software7.chatroom.interfaces.OnValidateNewContact
import com.software7.chatroom.models.Contacts
import com.software7.chatroom.views.AddContactView

class AddNewUserPresenterImpl(val addContactView: AddContactView) : AddNewUserPresenter, OnValidateNewContact {


    private var addNewUserInteract : AddNewUserInteract = AddNewUserInteractImpl()

    override fun validateUser(userName: String, email: String) {
      addNewUserInteract.addUser(this,userName,email)
    }

    override fun notifyRecyclerView(contact: Contacts) {
        addContactView.notifyRecyclerView(contact)
    }

    override fun showErrorAddContact(errorMessage: String) {
        addContactView.showErrorAddContact(errorMessage)
    }
    override fun hideProgressBar() {
        addContactView.hideProgressBar()
    }

    override fun showProgressBar() {
        addContactView.showProgressBar()
    }
}