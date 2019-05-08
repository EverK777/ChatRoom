package com.software7.chatroom.interfaces

import com.software7.chatroom.models.Contacts

interface OnValidateNewContact {

    fun notifyRecyclerView(contact : Contacts)
    fun showErrorAddContact(errorMessage: String)
    fun hideProgressBar()
    fun showProgressBar()
}