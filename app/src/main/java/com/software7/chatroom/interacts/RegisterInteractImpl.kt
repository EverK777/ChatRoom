package com.software7.chatroom.interacts

import com.software7.chatroom.ChatRoom
import com.software7.chatroom.data.FirebaseDataBaseReference
import com.software7.chatroom.interfaces.OnValidateRegister
import com.software7.chatroom.models.User
import com.google.firebase.auth.UserProfileChangeRequest
import com.software7.chatroom.data.ChatPreferences
import com.software7.chatroom.utils.EndToEndEncrypt


class RegisterInteractImpl : RegisterInteract {


    override fun createNewUser(onValidateRegister: OnValidateRegister, nameUser: String, email: String, password: String) {

        if(nameUser.isNotEmpty()&& email.isNotEmpty() && password.isNotEmpty()){
            onValidateRegister.showProgressBar()
            ChatRoom.auth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        //get user after register
                        ChatRoom.userAuth = ChatRoom.auth!!.currentUser
                        //set display name
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(nameUser).build()
                        // update fireBase
                        ChatRoom.userAuth!!.updateProfile(profileUpdates)
                        // update current user
                        ChatRoom.user = User(ChatRoom.userAuth!!.uid,nameUser, email,ArrayList())

                        // save user to fireBase database
                        saveUserToDatabase(ChatRoom.userAuth!!.uid,nameUser, email)

                        saveAndCreateKeys(ChatRoom.userAuth!!.uid)

                        onValidateRegister.hideProgressBar()
                        onValidateRegister.navigateToHome()

                    }else{
                        onValidateRegister.showErrorRegister("Datos invalidos , verifique la información")
                        onValidateRegister.hideProgressBar()
                    }
                }
        }
        else{
            onValidateRegister.showErrorRegister("Llene todos los campos")
        }
    }


    private fun saveUserToDatabase(idUser:String,nameUser: String, email: String){
        val user = User(idUser,nameUser,email,null)
        FirebaseDataBaseReference.userReference.child(idUser).setValue(user)
    }
    private fun saveAndCreateKeys(userId:String){
        try{
            //generateKeyPair
            val keyMap : Map<String,Any> = EndToEndEncrypt.initKey()
            val publicKey = EndToEndEncrypt.getPublicKey(keyMap)
            val privateKey = EndToEndEncrypt.getPrivateKey(keyMap)
            //save public key to firebase
            FirebaseDataBaseReference.savePublicKey(publicKey,userId)

            // save pair of jeys to firebase
            val editor =  ChatRoom.preferences!!.edit()
            editor.putString(ChatPreferences.publicKeyReference,publicKey)
            editor.putString(ChatPreferences.privateKeyReference,privateKey)
            editor.apply()
            ChatRoom.publicKey = publicKey
            ChatRoom.privateKey = privateKey

        }catch (e:Exception){
            e.printStackTrace()
        }


    }
}