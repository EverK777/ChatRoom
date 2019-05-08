package com.software7.chatroom.interacts

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.software7.chatroom.ChatRoom
import com.software7.chatroom.data.FirebaseDataBaseReference
import com.software7.chatroom.data.InternalDataBase
import com.software7.chatroom.interfaces.OnFillRecyclerView
import com.software7.chatroom.interfaces.OnReceiveMessage
import com.software7.chatroom.interfaces.OnSendMessage
import com.software7.chatroom.models.Message
import com.software7.chatroom.utils.EndToEndEncrypt
import java.util.*
import kotlin.collections.ArrayList

class ChatInteractImpl(private val idContact:String): ChatInteract {


    override fun sendMessage(onSendMessage: OnSendMessage, message: String) {

        if(message.isNotEmpty()){
            val calendar = Calendar.getInstance()
            calendar.timeInMillis

            val milliSecond : Int = calendar.get(Calendar.MILLISECOND)
            val second : Int = calendar.get(Calendar.SECOND)
            val minute : Int = calendar.get(Calendar.MINUTE)
            val hour   : Int = calendar.get(Calendar.HOUR)
            val day    : Int = calendar.get(Calendar.DAY_OF_MONTH)
            val month  : Int = calendar.get(Calendar.MONTH)
            val year   : Int = calendar.get(Calendar.YEAR)
            val idMessage = ChatRoom.user!!.nameUser +
                    year.toString()   +
                    month.toString()  +
                    day.toString()    +
                    hour.toString()   +
                    minute.toString() +
                    second.toString() +
                    milliSecond.toString()

            val messageSend = Message(idMessage
                ,hour,minute,second,milliSecond,day,month,year,message,
                idContact,0,ChatRoom.publicKey!!,"0","0")


            onSendMessage.onSend(messageSend)


            val internalDataBase = InternalDataBase(ChatRoom.context!!)

            internalDataBase.saveMessage(messageSend)


      // get public key from server
            val query =  FirebaseDataBaseReference.userReference.orderByChild("idUser").equalTo(idContact)
            query.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (child in dataSnapshot.children) {
                          val  publicKey = child.child("publicKey").value.toString()
                            //encrypt message
                            val encryptMessage  = Message(idMessage
                                ,hour,minute,second,milliSecond,day,month,year,encryptMessage(messageSend.content,publicKey)!!,
                                ChatRoom.user!!.idUser ,0,publicKey,"1","1")

                            //send message to firebase
                            FirebaseDataBaseReference.userReference
                                .child(idContact)
                                .child(FirebaseDataBaseReference.messagesReferene)
                                .child(FirebaseDataBaseReference.inboxReference)
                                .child(ChatRoom.user!!.idUser)
                                .child(encryptMessage.idMessage)
                                .setValue(encryptMessage)
                        }
                    }
                }
            })
        }
    }
    override fun receiveMessages(onReceiveMessage: OnReceiveMessage) {

       val childEventListener =  FirebaseDataBaseReference.userReference
              .child(ChatRoom.user!!.idUser)
              .child(FirebaseDataBaseReference.messagesReferene)
              .child(FirebaseDataBaseReference.inboxReference)
              .child(idContact)
              .orderByChild("idMessage")
              .addChildEventListener(object :
            ChildEventListener {
                  override fun onCancelled(p0: DatabaseError) {
                  }
                  override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                  }
                  override fun onChildChanged(data: DataSnapshot, p1: String?) {
                  }
                  override fun onChildAdded(data: DataSnapshot, p1: String?) {

                      if(data.child("received").value.toString() =="1"){
                          val decriptedMessage = decryptMessage(data.child("content").value.toString(),
                              data.child("publicKey").value.toString())

                          // si no se pudo desencriptar error
                          if(decriptedMessage == null || decriptedMessage.isEmpty() ){
                              data.ref.removeValue()
                          }else{

                              val calendar = Calendar.getInstance()
                              calendar.timeInMillis

                              val milliSecond : Int = calendar.get(Calendar.MILLISECOND)
                              val second : Int = calendar.get(Calendar.SECOND)
                              val minute : Int = calendar.get(Calendar.MINUTE)
                              val hour   : Int = calendar.get(Calendar.HOUR)
                              val day    : Int = calendar.get(Calendar.DAY_OF_MONTH)
                              val month  : Int = calendar.get(Calendar.MONTH)
                              val year   : Int = calendar.get(Calendar.YEAR)

                              val message= Message(data.child("idMessage").value.toString(),
                                  hour ,
                                  minute,
                                  second,
                                  milliSecond,
                                  day,
                                  month,
                                  year,
                                  decriptedMessage,
                                  idContact,
                                  1,data.child("publicKey").value.toString(),"0","0")

                              val internalDataBase = InternalDataBase(ChatRoom.context!!)

                              internalDataBase.saveMessage(message)

                              updateFirebase(message.idMessage,idContact)
                              updateFirebaseReceived(message.idMessage,idContact)

                              onReceiveMessage.onRecieve(message)
                              //  data.ref.removeValue()
                          }
                      }

                  }
                  override fun onChildRemoved(p0: DataSnapshot) {
                  }
        })

        onReceiveMessage.asignListener(childEventListener)

    }

    override fun initRecyclerView(onFillRecyclerView: OnFillRecyclerView) {
        val messages :ArrayList<Message> = ArrayList()

        val internalDataBase = InternalDataBase(ChatRoom.context!!)

        val cursor = internalDataBase.getChatMessage(idContact)

        if(cursor.moveToFirst()){
             for(i in 0 until cursor.count){
                 messages.add(Message(cursor.getString(1),
                     cursor.getInt(5),
                     cursor.getInt(4),
                     cursor.getInt(3),
                     cursor.getInt(2),
                     cursor.getInt(6),
                     cursor.getInt(7),
                     cursor.getInt(8),
                     cursor.getString(9),
                     cursor.getString(11),
                     cursor.getInt(10),
                     ChatRoom.publicKey!!,"0","0"))

                 updateInternal(cursor.getString(1))
                 cursor.moveToNext()
               }
        }

        onFillRecyclerView.onFill(messages)
    }

    // method that encrytMessage

    private fun encryptMessage(message:String, publicKey :String) :String? {

       var messageEncrypted : String ?= null
        try{
            val messageBytes  = message.toByteArray()
            val encodeMessage = EndToEndEncrypt.encryptByPublicKey(messageBytes,publicKey)
            // message to send to firebase
            messageEncrypted = EndToEndEncrypt.encryptBASE64(encodeMessage)
        }catch(e:Exception){
            e.printStackTrace()
        }

        return messageEncrypted
    }
    // method that decryptMessage
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
    //method that update message
    private fun updateFirebase(idMessageRef : String,idContact:String){
        FirebaseDataBaseReference.userReference
            .child(ChatRoom.user!!.idUser)
            .child(FirebaseDataBaseReference.messagesReferene)
            .child(FirebaseDataBaseReference.inboxReference)
            .child(idContact)
            .child(idMessageRef)
            .child("seen")
            .setValue("0")

    }
    private fun updateFirebaseReceived(idMessageRef : String,idContact:String){
        FirebaseDataBaseReference.userReference
            .child(ChatRoom.user!!.idUser)
            .child(FirebaseDataBaseReference.messagesReferene)
            .child(FirebaseDataBaseReference.inboxReference)
            .child(idContact)
            .child(idMessageRef)
            .child("received")
            .setValue("0")
    }

    //update internal on childCHange
    private fun updateInternal(idMessage :String){
       val internalDataBase = InternalDataBase(ChatRoom.context!!)
        internalDataBase.updateMessages(idMessage)
    }



}