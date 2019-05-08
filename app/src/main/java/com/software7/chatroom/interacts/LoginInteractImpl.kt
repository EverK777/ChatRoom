package com.software7.chatroom.interacts

import com.software7.chatroom.interfaces.OnValidateLogin
import com.software7.chatroom.ChatRoom
import com.software7.chatroom.data.ChatPreferences
import com.software7.chatroom.data.FirebaseDataBaseReference
import com.software7.chatroom.models.User
import com.software7.chatroom.utils.EndToEndEncrypt


class LoginInteractImpl : LoginInteract {


    override fun LoginUser(onValidateLogin: OnValidateLogin, email: String, password: String) {
        onValidateLogin.showProgressBar()
        ChatRoom.auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    //get user after register
                    ChatRoom.userAuth = ChatRoom.auth!!.currentUser
                    //set display name
                    ChatRoom.user = User(ChatRoom.userAuth!!.uid,ChatRoom.userAuth!!.displayName!!, email,ArrayList())

                    saveAndCreateKeys(ChatRoom.userAuth!!.uid)

                    onValidateLogin.navigateToHome()

                }else{
                    onValidateLogin.showErrorLogin("Datos invalidos , verifique la información")
                }
                onValidateLogin.hideProgressBar()
            }
    }
    private fun saveAndCreateKeys(userId:String){
        try{
            //generateKeyPair
            val keyMap : Map<String,Any> = EndToEndEncrypt.initKey()
            val publicKey = EndToEndEncrypt.getPublicKey(keyMap)
            val privateKey = EndToEndEncrypt.getPrivateKey(keyMap)
            //save public key to firebase
            FirebaseDataBaseReference.savePublicKey(publicKey,userId)

            // save pair of jeys to SharedPreferences
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