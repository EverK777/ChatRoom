package com.software7.chatroom.interacts

import com.software7.chatroom.interfaces.OnValidateLogin

interface LoginInteract {

    fun LoginUser(onValidateLogin: OnValidateLogin, email:String, password:String)


}