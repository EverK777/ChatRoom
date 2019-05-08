package com.software7.chatroom.ui_ux.fragments.login_fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.software7.chatroom.R
import com.software7.chatroom.presenters.RegisterPresenter
import com.software7.chatroom.presenters.RegisterPresenterImpl
import com.software7.chatroom.ui_ux.activities.HomeActivity
import com.software7.chatroom.ui_ux.dialogs.DialogLoading
import com.software7.chatroom.views.RegisterView

class FragmentRegister : Fragment(), RegisterView{

    private var dialogLoading : DialogLoading ?=null
    private var registerPresenter : RegisterPresenter = RegisterPresenterImpl(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.profile_register_fragment, container, false);
        val backToLoginTextView = v.findViewById<TextView>(R.id.backTextView)
        val userEditText = v.findViewById<TextInputEditText>(R.id.userEditText)
        val emailEditText = v.findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = v.findViewById<TextInputEditText>(R.id.passworfEditText)
        val buttonRegister = v.findViewById<AppCompatButton>(R.id.registerButton)
        val viewPager = activity!!.findViewById<ViewPager>(R.id.loginPager)
        backToLoginTextView.setOnClickListener {viewPager.currentItem = 0}
          buttonRegister.setOnClickListener {
          validateRegister(userEditText.text.toString(),
              emailEditText.text.toString(),
              passwordEditText.text.toString())
      }

        dialogLoading = DialogLoading(context!!,"Registrando ... \n Por favor espere")

        return v

    }

    private fun validateRegister(nameUser:String, email:String, password:String){
        registerPresenter.validateRegisterForm(nameUser,email,password)
    }


    override fun showErrorRegister(errorMessage: String) {
        Toast.makeText(context,errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        dialogLoading!!.hideDialog()
    }
    override fun showProgressBar() {
        dialogLoading!!.showDialog()
    }
    override fun navigateToHome() {
        startActivity(Intent(context,HomeActivity::class.java))
        activity!!.finish()
    }

}