package com.software7.chatroom.ui_ux.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.software7.chatroom.ChatRoom
import com.software7.chatroom.R
import com.software7.chatroom.data.FirebaseDataBaseReference
import com.software7.chatroom.models.Message
import com.software7.chatroom.models.User
import com.software7.chatroom.presenters.ChatPresenter
import com.software7.chatroom.presenters.ChatPresenterImpl
import com.software7.chatroom.ui_ux.adapters.MessagesAdapter
import com.software7.chatroom.views.ChatView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Array

class ChatActivity : AppCompatActivity(),ChatView {

    private var messages: ArrayList<Message> = ArrayList()
    private var chatPresenter : ChatPresenter ?= null
    private var messagesAdapter : MessagesAdapter ?=null
    private var idContact : String ?=null
    private var contactName : String ? =null
    private var childEventListener : ChildEventListener ? = null
    private var paused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get extras
        if(intent.extras !=null){
            idContact = intent.extras.getString("idContact")
            contactName = intent.extras.getString("userName")
        }

        supportActionBar!!.title = contactName
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        chatPresenter = ChatPresenterImpl(this,idContact!!)

        sendMessageFab.setOnClickListener{sendMessage()}

        setAdapterRecycler()
        initRecyclerView()

        receiveMessage()


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home){
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun  sendMessage(){
       chatPresenter!!.sendMessage(messageEditText.text.toString())
        messageEditText.setText("")
    }

    private fun receiveMessage(){
        chatPresenter!!.recieveMessages()
    }

    private fun initRecyclerView(){
        chatPresenter!!.initRecycler()
    }

    private fun setAdapterRecycler(){
        messagesAdapter = MessagesAdapter(messages)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        linearLayoutManager.stackFromEnd = true
        messagesRecyclerView!!.layoutManager = linearLayoutManager
        messagesRecyclerView!!.adapter = messagesAdapter
    }

    private fun cancellListener(){
        FirebaseDataBaseReference.userReference
            .child(ChatRoom.user!!.idUser)
            .child(FirebaseDataBaseReference.messagesReferene)
            .child(FirebaseDataBaseReference.inboxReference)
            .child(idContact!!)
            .orderByChild("idMessage")
            .removeEventListener(childEventListener!!)
    }

    override fun notifyRecyclerViewSendMessage(message: Message) {
        messages.add(message)
        messagesAdapter!!.notifyItemInserted(messages.size)
        messagesRecyclerView!!.smoothScrollToPosition(messages.size-1)
    }

    override fun notifyRecyclerViewReciveMessage(message: Message) {
        messages.add(message)
        messagesAdapter!!.notifyItemInserted(messages.size)
        messagesRecyclerView!!.smoothScrollToPosition(messages.size-1)

    }
    override fun fillRecyclerView(messages: ArrayList<Message>) {
           this.messages.addAll(messages)
           messagesAdapter!!.notifyDataSetChanged()
    }

    override fun initListener(childEventListener: ChildEventListener) {
        this.childEventListener = childEventListener
    }


    override fun onDestroy() {
        super.onDestroy()
        if(childEventListener!=null){
           cancellListener()
        }
    }

    override fun onPause() {
        super.onPause()
        paused = true

       cancellListener()

    }

    override fun onResume() {
        super.onResume()
        if(paused){
            receiveMessage()
        }
    }
}
