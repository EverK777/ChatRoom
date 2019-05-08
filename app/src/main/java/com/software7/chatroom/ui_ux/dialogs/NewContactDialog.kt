package com.software7.chatroom.ui_ux.dialogs

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.software7.chatroom.R
import com.software7.chatroom.models.Contacts
import com.software7.chatroom.presenters.AddNewUserPresenter
import com.software7.chatroom.presenters.AddNewUserPresenterImpl
import com.software7.chatroom.ui_ux.adapters.ContactsAdapter
import com.software7.chatroom.views.AddContactView

class NewContactDialog (var context: Context,val contacts: ArrayList<Contacts>,val contactsAdapter: ContactsAdapter,val recyclerView: RecyclerView, val textView: TextView): AddContactView {


    private var dialog: Dialog ?= null
    private var addNewUserPresenter : AddNewUserPresenter ?=null
    private var dialogLoading :DialogLoading ?=null


    fun showDialog(){
        dialog = Dialog(context)

        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.contact_dialog)

        val nameUser = dialog!!.findViewById<EditText>(R.id.userName)
        val email = dialog!!.findViewById<EditText>(R.id.emailUser)
        val addContact = dialog!!.findViewById<AppCompatButton>(R.id.addUser)
        addNewUserPresenter = AddNewUserPresenterImpl(this)

        addContact.setOnClickListener {addNewUserPresenter!!.validateUser(nameUser.text.toString(),email.text.toString())}


        dialogLoading = DialogLoading(context,"Agregando usuario ... \n Por favor espere")

        dialog!!.show()


    }

    override fun notifyRecyclerView(contact : Contacts) {
        recyclerView.visibility = View.VISIBLE
        textView.visibility = View.GONE
        contacts.add(contact)
        contactsAdapter.notifyItemInserted(contacts.size)
        dialog!!.dismiss()

    }

    override fun showErrorAddContact(errorMessage: String) {
        Toast.makeText(context,errorMessage,Toast.LENGTH_LONG).show()
    }
    override fun hideProgressBar() {
        dialogLoading!!.hideDialog()

    }

    override fun showProgressBar() {
        dialogLoading!!.showDialog()
    }


}