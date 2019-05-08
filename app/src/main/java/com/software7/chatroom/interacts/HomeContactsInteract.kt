package com.software7.chatroom.interacts

import com.software7.chatroom.interfaces.OnGetContacts

interface HomeContactsInteract {
    fun getContacts(onGetContacts: OnGetContacts)
}