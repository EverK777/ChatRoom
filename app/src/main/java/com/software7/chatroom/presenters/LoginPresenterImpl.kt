package com.software7.chatroom.presenters

import com.software7.chatroom.interacts.LoginInteract
import com.software7.chatroom.interacts.LoginInteractImpl
import com.software7.chatroom.interfaces.OnValidateLogin
import com.software7.chatroom.views.LoginView

class LoginPresenterImpl(private val loginView: LoginView) : LoginPresenter, OnValidateLogin {

    private val loginInteract : LoginInteract = LoginInteractImpl()

    override fun validateLoginForm(email: String, password: String) {
        loginInteract.LoginUser(this,email,password)
    }

    override fun showErrorLogin(errorMessage: String) {
        loginView.showErrorLogin(errorMessage)
    }

    override fun hideProgressBar() {
        loginView.hideProgressBar()
    }

    override fun showProgressBar() {
        loginView.showProgressBar()
    }

    override fun navigateToHome() {
        loginView.navigateToHome()
    }

}