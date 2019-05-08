package com.software7.chatroom.interfaces

interface OnValidateRegister {
    fun showErrorRegister(errorMessage : String)
    fun hideProgressBar()
    fun showProgressBar()
    fun navigateToHome()
}