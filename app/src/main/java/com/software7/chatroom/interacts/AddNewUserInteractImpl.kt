package com.software7.chatroom.interacts

import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.software7.chatroom.ChatRoom
import com.software7.chatroom.data.FirebaseDataBaseReference
import com.software7.chatroom.data.InternalDataBase
import com.software7.chatroom.interfaces.OnValidateNewContact
import com.software7.chatroom.models.Contacts

class AddNewUserInteractImpl : AddNewUserInteract {


    override fun addUser(onValidateNewContact: OnValidateNewContact, userName: String, email: String) {
        var contacts : Contacts ? = null

        onValidateNewContact.showProgressBar()
        val internalDataBase = InternalDataBase(ChatRoom.context!!)

        when {
            // if the user has already added contact
            internalDataBase.getContactByEmail(email).moveToFirst() -> {
                onValidateNewContact.showErrorAddContact("El usuario ya esta agregado")
                onValidateNewContact.hideProgressBar()
            }
            //if the user wants to add himself
            email == ChatRoom.user!!.email -> {
                onValidateNewContact.showErrorAddContact("No se puede agregar a usted mismo")
                onValidateNewContact.hideProgressBar()
            }
            else -> {
                val query =  FirebaseDataBaseReference.userReference.orderByChild("email").equalTo(email)

                query.addListenerForSingleValueEvent(object: ValueEventListener{

                    override fun onCancelled(p0: DatabaseError) {
                        onValidateNewContact.hideProgressBar()
                    }
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(dataSnapshot.exists()){
                            for (child in dataSnapshot.children) {
                                var userNameToSave = ""
                                if(userName.isEmpty()){
                                    userNameToSave = child.child("nameUser").value.toString()
                                }else{
                                    userNameToSave = userName
                                }

                                contacts = Contacts(
                                        child.child("idUser").value.toString(),
                                        userNameToSave,
                                        child.child("email").value.toString())
                            }


                            internalDataBase.saveContact(contacts!!)
                            saveContactToDatabase(contacts!!)
                            onValidateNewContact.notifyRecyclerView(contacts!!)
                        }else{
                            onValidateNewContact.showErrorAddContact("Usuario no encontrado. Intente otro Correo")
                        }
                        onValidateNewContact.hideProgressBar()
                    }

                })
            }
        }


    }
    private fun saveContactToDatabase(contact:Contacts){
        FirebaseDataBaseReference.userReference
            .child(ChatRoom.user!!.idUser)
            .child("contacts").child(contact.idContact)
            .setValue(contact)
    }



}