package com.software7.chatroom.views

interface RegisterView {

    fun showErrorRegister(errorMessage : String)
    fun hideProgressBar()
    fun showProgressBar()
    fun navigateToHome()

}