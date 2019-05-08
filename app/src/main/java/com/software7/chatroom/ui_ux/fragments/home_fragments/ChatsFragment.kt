package com.software7.chatroom.ui_ux.fragments.home_fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.ChildEventListener
import com.software7.chatroom.ChatRoom
import com.software7.chatroom.R
import com.software7.chatroom.data.FirebaseDataBaseReference
import com.software7.chatroom.models.Message
import com.software7.chatroom.presenters.HomeChatsPresenter
import com.software7.chatroom.presenters.HomeChatsPresenterImpl
import com.software7.chatroom.ui_ux.activities.ChatActivity
import com.software7.chatroom.ui_ux.adapters.ChatsAdapter
import com.software7.chatroom.ui_ux.dialogs.ModalBottomDialog
import com.software7.chatroom.ui_ux.dialogs.NewContactDialog
import com.software7.chatroom.views.HomeChatsView
import kotlinx.android.synthetic.main.chats_fragment.*

class ChatsFragment : Fragment(), HomeChatsView{

    private var messages : ArrayList<Message> = ArrayList()
    private var contactNames : ArrayList<String> = ArrayList()
    private var chatsAdapter : ChatsAdapter ? = null
    private var homeChatsPresenter : HomeChatsPresenter ?=null
    private var recyclerChats : RecyclerView ? = null
    private var childEventListener : ChildEventListener? = null
    private var emptyMessage : TextView ? = null
    private var isPused = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.chats_fragment, container, false)
        recyclerChats = v.findViewById<RecyclerView>(R.id.chatsRecycler)
        recyclerChats!!.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        chatsAdapter = ChatsAdapter(messages,contactNames)
        recyclerChats!!.adapter = chatsAdapter
        emptyMessage = v.findViewById(R.id.emptyMessage)

        homeChatsPresenter = HomeChatsPresenterImpl(this)

        // this method init the recyclerview
        homeChatsPresenter!!.getFinalMessagesPerChat()

        chatsAdapter!!.setOnItemClickListener(object: ChatsAdapter.OnOpenNewChat{
            override fun onClick(position: Int) {
                    openChat(position)
            }

        })

        startReceivingMessages()

        return v
    }

    override fun initRecyclerView(messages: ArrayList<Message>, contactNames : ArrayList<String>) {
        recyclerChats!!.visibility = View.VISIBLE
        emptyMessage!!.visibility = View.GONE
        this.messages.addAll(messages)
        this.contactNames.addAll(contactNames)
        chatsAdapter!!.notifyDataSetChanged()
    }

    override fun showEmpty() {
        recyclerChats!!.visibility = View.GONE
        emptyMessage!!.visibility = View.VISIBLE
    }


    override fun notifyRecyclerViewReciveMessage(messages: Message, contactName: String, position: Int?) {
        if(position != null){
            this.messages[position].content = messages.content
            this.messages[position].hourMessage = messages.hourMessage
            this.messages[position].minuteMessage = messages.minuteMessage
            this.messages[position].seen = (this.messages[position].seen .toInt() +  messages.seen.toInt()).toString()
            chatsAdapter!!.notifyDataSetChanged()
        }else{
            this.messages.add(messages)
            this.contactNames.add(contactName)
            recyclerChats!!.visibility = View.VISIBLE
            emptyMessage!!.visibility = View.GONE
            chatsAdapter!!.notifyItemInserted(0)
        }

    }

    override fun initListener(childEventListener: ChildEventListener) {
        this.childEventListener = childEventListener
    }

    private fun openChat(position:Int){
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("idContact", messages[position].emisor)
        intent.putExtra("userName", contactNames[position])
        startActivity(intent)
    }

    private fun startReceivingMessages(){
        homeChatsPresenter!!.recieveMessages(this.messages)

    }


    private fun cancelListener(){
        FirebaseDataBaseReference.userReference
            .child(ChatRoom.user!!.idUser)
            .child(FirebaseDataBaseReference.messagesReferene)
            .child(FirebaseDataBaseReference.inboxReference)
            .removeEventListener(childEventListener!!)
    }


    override fun onPause() {
        super.onPause()
            isPused = true
            messages.clear()
            contactNames.clear()
            cancelListener()
    }

    override fun onResume() {
        super.onResume()
        if(isPused){
            homeChatsPresenter!!.getFinalMessagesPerChat()
            startReceivingMessages()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelListener()
    }

}