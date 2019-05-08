package com.software7.chatroom.presenters

interface RegisterPresenter {
    fun validateRegisterForm(nameUser:String, email:String, password:String)
}