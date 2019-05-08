package com.software7.chatroom.views

import com.software7.chatroom.models.Contacts

interface AddContactView {

    fun notifyRecyclerView(contact : Contacts)
    fun showErrorAddContact(errorMessage: String)
    fun hideProgressBar()
    fun showProgressBar()
}