package com.software7.chatroom.ui_ux.fragments.login_fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.software7.chatroom.R
import com.software7.chatroom.presenters.LoginPresenter
import com.software7.chatroom.presenters.LoginPresenterImpl
import com.software7.chatroom.ui_ux.activities.HomeActivity
import com.software7.chatroom.ui_ux.dialogs.DialogLoading
import com.software7.chatroom.views.LoginView

class FragmentLogin: Fragment(), LoginView {

    private var dialogLoading : DialogLoading?=null
    private var loginPresenter : LoginPresenter = LoginPresenterImpl(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.login_fragment, container, false)

        val registerTextView = v.findViewById<TextView>(R.id.registerTextView)
        val viewPager        = activity!!.findViewById<ViewPager>(R.id.loginPager)
        val emailTextView    =  v!!.findViewById<TextView>(R.id.userEditText)
        val passwordEditText =  v.findViewById<TextView>(R.id.passworEditText)
        val loginButton      =  v.findViewById<AppCompatButton>(R.id.loginButton)

        registerTextView.setOnClickListener {viewPager.currentItem = 1}
        loginButton.setOnClickListener { validateLogin(emailTextView.text.toString(),passwordEditText.text.toString()) }
        dialogLoading = DialogLoading(context!!,"Accediendo ... \n Por favor espere")

        return v

    }


    private fun validateLogin(email:String, password:String){
        loginPresenter.validateLoginForm(email,password)
    }

    override fun showErrorLogin(errorMessage: String) {
        Toast.makeText(context,errorMessage, Toast.LENGTH_LONG).show()

    }

    override fun hideProgressBar() {
        dialogLoading!!.hideDialog()
    }

    override fun showProgressBar() {
        dialogLoading!!.showDialog()
    }

    override fun navigateToHome() {
        startActivity(Intent(context, HomeActivity::class.java))
        activity!!.finish()
    }
}