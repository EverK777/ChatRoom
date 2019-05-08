package com.software7.chatroom.interfaces

interface OnValidateLogin {
    fun showErrorLogin(errorMessage : String)
    fun hideProgressBar()
    fun showProgressBar()
    fun navigateToHome()
}