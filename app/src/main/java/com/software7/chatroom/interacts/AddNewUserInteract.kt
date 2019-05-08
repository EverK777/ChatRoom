package com.software7.chatroom.interacts

import com.software7.chatroom.interfaces.OnValidateNewContact

interface AddNewUserInteract {

    fun addUser(onValidateNewContact: OnValidateNewContact,userName:String, email:String)
}