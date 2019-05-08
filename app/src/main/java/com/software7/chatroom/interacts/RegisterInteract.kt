package com.software7.chatroom.interacts

import com.software7.chatroom.interfaces.OnValidateRegister

interface RegisterInteract {

    fun createNewUser(onValidateRegister: OnValidateRegister, nameUser:String, email:String, password:String)
}