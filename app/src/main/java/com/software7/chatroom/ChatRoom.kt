package com.software7.chatroom

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.software7.chatroom.data.ChatPreferences
import com.software7.chatroom.data.FirebaseDataBaseReference
import com.software7.chatroom.models.User

class ChatRoom : Application() {


    companion object{
        var auth: FirebaseAuth ?= null
        var userAuth : FirebaseUser ?= null
        var user : User ?=null
        var preferences : SharedPreferences ? =null
        var publicKey : String ?=null
        var privateKey : String ?=null
        var context : Context? = null

    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        auth =  FirebaseAuth.getInstance()
        userAuth = auth!!.currentUser
        preferences = ChatPreferences().getPreferences(this)
        if(userAuth!=null){
            user = User(userAuth!!.uid, userAuth!!.displayName!!, userAuth!!.email!!, ArrayList())
        }

        context = this
        publicKey  = preferences!!.getString(ChatPreferences.publicKeyReference,"")
        privateKey = preferences!!.getString(ChatPreferences.privateKeyReference,"")


    }

}