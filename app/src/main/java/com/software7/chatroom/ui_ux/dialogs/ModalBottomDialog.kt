package com.software7.chatroom.ui_ux.dialogs

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.software7.chatroom.R
import com.software7.chatroom.models.Contacts
import com.software7.chatroom.presenters.HomeContactsPresenter
import com.software7.chatroom.presenters.HomeContactsPresenterImpl
import com.software7.chatroom.ui_ux.activities.ChatActivity
import com.software7.chatroom.ui_ux.adapters.ContactsAdapter
import com.software7.chatroom.views.HomeContactView

class ModalBottomDialog : BottomSheetDialogFragment(), HomeContactView {

    private var homeContactsPresenter : HomeContactsPresenter?=null
    private var contactsRecycler :RecyclerView ? = null
    private var contactsAdapter : ContactsAdapter?=null
    private var contacts : ArrayList<Contacts> = ArrayList()
    private var emptyMessage : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.modal_bottom_sheet, container, false)
        contactsRecycler = v.findViewById(R.id.contactsRecycler)
        emptyMessage = v.findViewById(R.id.emptyMessage)
        contactsRecycler!!.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        contactsAdapter = ContactsAdapter(contacts,true)
        contactsRecycler!!.adapter = contactsAdapter
        contactsAdapter!!.setOnItemClickListener(object : ContactsAdapter.OnSelectUserListener{
            override fun onClick(position: Int) {
                    openChat(position)
            }
        })
        homeContactsPresenter = HomeContactsPresenterImpl(this)
        // this method init the recyclerview
        homeContactsPresenter!!.getContacts()



        return v
    }

    override fun initRecyclerView(contacts: ArrayList<Contacts>) {
        contactsRecycler!!.visibility = View.VISIBLE
        emptyMessage!!.visibility = View.GONE
        this.contacts.addAll(contacts)
        contactsAdapter!!.notifyDataSetChanged()    }

    override fun showEmpty() {
        contactsRecycler!!.visibility = View.GONE
        emptyMessage!!.visibility = View.VISIBLE


    }

    private fun openChat(position:Int){
        val intent = Intent(context,ChatActivity::class.java)
        intent.putExtra("idContact", contacts[position].idContact)
        intent.putExtra("userName", contacts[position].nameContact)
        startActivity(intent)
        this.dismiss()
    }
}