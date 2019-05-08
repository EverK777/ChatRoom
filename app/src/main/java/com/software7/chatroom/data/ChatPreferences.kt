package com.software7.chatroom.data

import android.content.Context
import android.content.SharedPreferences

class ChatPreferences {


    private var  preferences : SharedPreferences?= null
    companion object{
        const val publicKeyReference  = "publicKey"
        const val privateKeyReference = "privateKey"

    }


    fun getPreferences(context: Context) : SharedPreferences {
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        return preferences!!
    }
}