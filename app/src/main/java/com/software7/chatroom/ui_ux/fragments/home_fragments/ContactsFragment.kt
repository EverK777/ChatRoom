package com.software7.chatroom.ui_ux.fragments.home_fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.software7.chatroom.R
import com.software7.chatroom.models.Contacts
import com.software7.chatroom.presenters.HomeContactsPresenter
import com.software7.chatroom.presenters.HomeContactsPresenterImpl
import com.software7.chatroom.ui_ux.adapters.ContactsAdapter
import com.software7.chatroom.views.HomeContactView
import kotlinx.android.synthetic.main.chats_fragment.*


class ContactsFragment : Fragment(), HomeContactView{

    private var homeContactsPresenter : HomeContactsPresenter?=null
     var contactsAdapter : ContactsAdapter ?=null
     var contacts : ArrayList<Contacts> = ArrayList()
     var  emptyMessageTV  : TextView ?=null
     var recyclerContacts : RecyclerView ?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.contacts_fragment,container,false)
         recyclerContacts= v.findViewById<RecyclerView>(R.id.contactsRecycler)
        recyclerContacts!!.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        contactsAdapter = ContactsAdapter(contacts,false)
        recyclerContacts!!.adapter = contactsAdapter
        emptyMessageTV = v.findViewById(R.id.emptyMessage)
        contactsAdapter!!.setOnItemClickListener(object : ContactsAdapter.OnSelectUserListener{
            override fun onClick(position: Int) {

            }
        })
        homeContactsPresenter = HomeContactsPresenterImpl(this)

        // this method init the recyclerview
        homeContactsPresenter!!.getContacts()


        return  v
    }

    override fun initRecyclerView(contacts: ArrayList<Contacts>) {
        emptyMessageTV !!.visibility = View.GONE
        recyclerContacts!!.visibility = View.VISIBLE
        this.contacts.addAll(contacts)
        contactsAdapter!!.notifyDataSetChanged()

    }

    override fun showEmpty() {
        emptyMessageTV !!.visibility = View.VISIBLE
        recyclerContacts!!.visibility = View.GONE

    }



}