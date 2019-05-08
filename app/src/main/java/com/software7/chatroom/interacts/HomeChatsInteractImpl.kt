package com.software7.chatroom.interacts

import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.software7.chatroom.ChatRoom
import com.software7.chatroom.data.FirebaseDataBaseReference
import com.software7.chatroom.data.InternalDataBase
import com.software7.chatroom.interfaces.OnGetMessages
import com.software7.chatroom.interfaces.OnReceiveMessageHome
import com.software7.chatroom.models.Contacts
import com.software7.chatroom.models.Message
import com.software7.chatroom.utils.EndToEndEncrypt
import java.util.*
import kotlin.collections.ArrayList

class HomeChatsInteractImpl : HomeChatsInteract {




    override fun receiveMessages(onReceiveMessageHome: OnReceiveMessageHome,messages:ArrayList<Message>) {
      val childEventListener =  FirebaseDataBaseReference.userReference
            .child(ChatRoom.user!!.idUser)
            .child(FirebaseDataBaseReference.messagesReferene)
            .child(FirebaseDataBaseReference.inboxReference)
            .orderByKey()
            .addChildEventListener(object : ChildEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                }
                override fun onChildChanged(data: DataSnapshot, p1: String?) {
                    data.children.forEach { snap->
                        if(snap.child("received").value.toString().toInt() == 1 && !checkMessageAlreadyAdded(snap.child("idMessage").value.toString())) {
                            val decriptedMessage = decryptMessage(
                                snap.child("content").value.toString(),
                                snap.child("publicKey").value.toString()
                            )
                            if (decriptedMessage != null) {
                                val calendar = Calendar.getInstance()
                                calendar.timeInMillis

                                val milliSecond: Int = calendar.get(Calendar.MILLISECOND)
                                val second: Int = calendar.get(Calendar.SECOND)
                                val minute: Int = calendar.get(Calendar.MINUTE)
                                val hour: Int = calendar.get(Calendar.HOUR)
                                val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
                                val month: Int = calendar.get(Calendar.MONTH)
                                val year: Int = calendar.get(Calendar.YEAR)

                                val messageToSend = Message(
                                    snap.child("idMessage").value.toString(),
                                    hour,
                                    minute,
                                    second,
                                    milliSecond,
                                    day,
                                    month,
                                    year,
                                    decriptedMessage,
                                    snap.child("emisor").value.toString(),
                                    1, snap.child("publicKey").value.toString(),
                                    "1", "0"
                                )

                                var newData = true
                                for (i in 0 until messages.size) {
                                    if (messages[i].emisor == messageToSend.emisor) {
                                        onReceiveMessageHome.onRecieve(messageToSend, "", i)
                                        newData = false
                                    }
                                }
                                if (newData) {
                                    var contactName: String = "Desconocido"
                                    val internalDataBase = InternalDataBase(ChatRoom.context!!)
                                    val cursor = internalDataBase.getContactById(messageToSend.emisor)
                                    // si se encuentra el contacto
                                    if (cursor.moveToFirst()) {
                                        contactName = cursor.getString(1)
                                    } else {
                                        internalDataBase.saveContact(Contacts(messageToSend.emisor, "Desconocido", "no_provide")
                                        )
                                    }
                                    onReceiveMessageHome.onRecieve(messageToSend, contactName, null)
                                }
                                saveToInternalDb(messageToSend)

                                updateFirebase(messageToSend.idMessage, messageToSend.emisor)

                            } else {
                                data.ref.removeValue()
                            }
                        }
                    }
                }
                override fun onChildAdded(data: DataSnapshot, p1: String?) {
                        data.children.forEach { snap->
                            // check if the message is already received or if there is a conversation with the user
                            if(snap.child("received").value.toString().toInt() == 1 && !checkMessageAlreadyAdded(snap.child("idMessage").value.toString()) ) {

                                val decriptedMessage = decryptMessage(
                                    snap.child("content").value.toString(),
                                    snap.child("publicKey").value.toString()
                                )

                                if (decriptedMessage != null) {
                                    val calendar = Calendar.getInstance()
                                    calendar.timeInMillis

                                    val milliSecond: Int = calendar.get(Calendar.MILLISECOND)
                                    val second: Int = calendar.get(Calendar.SECOND)
                                    val minute: Int = calendar.get(Calendar.MINUTE)
                                    val hour: Int = calendar.get(Calendar.HOUR)
                                    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
                                    val month: Int = calendar.get(Calendar.MONTH)
                                    val year: Int = calendar.get(Calendar.YEAR)

                                    val messageToSend = Message(
                                        snap.child("idMessage").value.toString(),
                                        hour,
                                        minute,
                                        second,
                                        milliSecond,
                                        day,
                                        month,
                                        year,
                                        decriptedMessage,
                                        snap.child("emisor").value.toString(),
                                        1, snap.child("publicKey").value.toString(),
                                        "1", "0"
                                    )
                                    var newData = true
                                    for (i in 0 until messages.size) {
                                        if (messages[i].emisor == messageToSend.emisor) {
                                            onReceiveMessageHome.onRecieve(messageToSend, "", i)
                                            newData = false
                                        }
                                    }
                                    if (newData) {
                                        var contactName: String = "Desconocido"
                                        val internalDataBase = InternalDataBase(ChatRoom.context!!)
                                        val cursor = internalDataBase.getContactById(messageToSend.emisor)
                                        // si se encuentra el contacto
                                        if (cursor.moveToFirst()) {
                                            contactName = cursor.getString(1)
                                        }else{
                                            internalDataBase.saveContact(Contacts(messageToSend.emisor,"Desconocido","no_provide"))
                                        }
                                        onReceiveMessageHome.onRecieve(messageToSend, contactName, null)
                                    }
                                    saveToInternalDb(messageToSend)

                                    updateFirebase(messageToSend.idMessage, messageToSend.emisor)

                                } else {
                                    data.ref.removeValue()
                                }
                            }
                     }
                }
                override fun onChildRemoved(p0: DataSnapshot) {
                }
            })

        onReceiveMessageHome.asignListener(childEventListener)
    }

    override fun getAllMessages(onGetMessages: OnGetMessages) {
        val messages : ArrayList<Message> = ArrayList()
        val names : ArrayList<String> = ArrayList()
        val data  = InternalDataBase(ChatRoom.context!!)

        val cursor =data.getAllMessages()

        if(cursor.moveToFirst()) {
            for (i in 0 until cursor.count) {

                messages.add(
                    Message(
                        cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getInt(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getInt(10),
                        ChatRoom.publicKey!!,
                        cursor.getInt(12).toString(),
                        cursor.getInt(13).toString())
                )
                names.add(cursor.getString(11))
                cursor.moveToNext()
            }

            onGetMessages.initRecyclerView(messages, names)

        }
        else{
            onGetMessages.showEmpty()
        }

    }

    private fun decryptMessage(hash : String,publicKeyInMessage: String):String?{

        for(i in 0 until publicKeyInMessage.length)
        {
            if(ChatRoom.publicKey!![i] != publicKeyInMessage[i]){
                return null
            }
        }
        try{
            val messageBytes  = EndToEndEncrypt.decryptBASE64(hash)
            val decodeMessage = EndToEndEncrypt.decryptByPrivateKey(messageBytes,ChatRoom.privateKey!!)
            val decodeStr = String(decodeMessage)

            return decodeStr

        }catch(e:Exception){
            e.printStackTrace()
        }
        return null
    }

    private fun saveToInternalDb(message:Message){
        val internalDataBase = InternalDataBase(ChatRoom.context!!)
        internalDataBase.saveMessage(message)
    }

    private fun updateFirebase(idMessageRef : String,idContact:String){
        FirebaseDataBaseReference.userReference
            .child(ChatRoom.user!!.idUser)
            .child(FirebaseDataBaseReference.messagesReferene)
            .child(FirebaseDataBaseReference.inboxReference)
            .child(idContact)
            .child(idMessageRef)
            .child("received")
            .setValue("0")
    }

    private fun checkMessageAlreadyAdded(idMessage:String):Boolean{

        val internalDataBase = InternalDataBase(ChatRoom.context!!)

        val cursor = internalDataBase.getMessageFromIdMessage(idMessage)

        return cursor.moveToFirst()
    }


}