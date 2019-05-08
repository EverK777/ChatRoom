package com.software7.chatroom.presenters

import com.software7.chatroom.interacts.RegisterInteract
import com.software7.chatroom.interacts.RegisterInteractImpl
import com.software7.chatroom.interfaces.OnValidateRegister
import com.software7.chatroom.views.RegisterView

class RegisterPresenterImpl(private val registerView: RegisterView) : RegisterPresenter, OnValidateRegister {


    private val registerInteract : RegisterInteract = RegisterInteractImpl()

    override fun validateRegisterForm(nameUser:String, email:String, password:String) {
        registerInteract.createNewUser(this,nameUser,email,password)

    }

    override fun showErrorRegister(errorMessage: String) {
        registerView.showErrorRegister(errorMessage)
    }

    override fun hideProgressBar() {
        registerView.hideProgressBar()
    }

    override fun showProgressBar() {
        registerView.showProgressBar()
    }

    override fun navigateToHome() {
        registerView.navigateToHome()
    }
}