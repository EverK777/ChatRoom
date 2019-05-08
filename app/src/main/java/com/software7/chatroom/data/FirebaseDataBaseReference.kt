package com.software7.chatroom.data

import com.google.firebase.database.*

object FirebaseDataBaseReference {

        val messagesReferene = "messages"
        val inboxReference   = "inbox"
        val sendReference    = "send"
        val database = FirebaseDatabase.getInstance()
        val userReference = database.getReference("users")

    fun savePublicKey(publicKey : String, idUser:String){
        FirebaseDataBaseReference.userReference.child(idUser).child("publicKey").setValue(publicKey)
    }



}