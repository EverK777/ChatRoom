package com.software7.chatroom.views

interface LoginView {
    fun showErrorLogin(errorMessage : String)
    fun hideProgressBar()
    fun showProgressBar()
    fun navigateToHome()
}